package com.palvai.properties;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.rmi.RemoteException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis2.Constants;
import org.apache.axis2.client.Options;
import org.apache.log4j.Logger;
import org.apache.xml.security.c14n.Canonicalizer;
import org.w3c.dom.Document;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.abb.sable.mi.ws_axis.AttachmentReferenceInfo;
import com.abb.sable.mi.ws_axis.AttachmentReferenceType;
import com.abb.sable.mi.ws_axis.AttachmentSignature;
import com.abb.sable.mi.ws_axis.AttachmentType;
import com.abb.sable.mi.ws_axis.InputStreamDataSource;
import com.abb.sable.mi.ws_axis.RequestAttInfo;
import com.abb.sable.mi.ws_axis.RequestDataType;
import com.abb.sable.mi.ws_axis.RequestSignature;
import com.abb.sable.mi.ws_axis.RequestType;
import com.abb.sable.mi.ws_axis.ResponseAttInfo;
import com.abb.sable.mi.ws_axis.ResponseDataType;

import com.abb.sable.mi.ws_axis_client.AttachmentInfo;
import com.abb.sable.mi.ws_axis_client.MiWebServiceClientNew;
import com.abb.sable.mi.ws_axis_client.MiWebServiceStub;
import com.abb.sable.mi.ws_axis_client.MiWebServiceClientNew.ConfigParameters;

public class MiWebServiceClientNew {

	public MiWebServiceClientNew() {

	}

	/**
	 * Main program for the MI web service client.
	 *
	 * @param args
	 *            command line arguments
	 *
	 * @throws RemoteException
	 */
	public void mainMethod(byte[] clientProperties, String inputXmlPath, String certificateFilePath)
			throws RemoteException {
		System.out.println("Started web service client...");

		// Get argument values
		String configFile = null;
		boolean submitAtt = true;

		// Load configuration
		System.out.println("Config File: " + configFile);
		ConfigParameters cfg = new ConfigParameters(clientProperties, inputXmlPath, certificateFilePath);
		System.out.println("Request Type: " + cfg.requestType);
		System.out.println("Admin Role: " + cfg.adminRole);

		String trustStore = cfg.trustStore;
		String trustStorePassword = cfg.trustStorePassword;

		// Set server security props
		if (!setSecurityProps(trustStore, trustStorePassword)) {
			System.out.println("Security settings had a problem ... exiting");
			System.exit(-1);
		}

		// Use proxy settings if specified in configuration
		if (cfg.proxySet) {
			System.getProperties().put("proxySet", "true");
			System.getProperties().put("proxyHost", cfg.proxyHost);
			System.getProperties().put("proxyPort", cfg.proxyPort);

		}

		if (cfg.attachmentOperation != null) {
			if (cfg.attachmentOperation.equalsIgnoreCase("retrieve")) {
				retrieveAttachment(cfg);
				return;
			} else if (cfg.attachmentOperation.equalsIgnoreCase("remove")) {
				removeAttachment(cfg);
				return;
			}
		}

		// Read input request file for the digital signature
		System.out.println("Reading input: " + cfg.getInputFileWithPath());

		// Create digital signature and terminate it with a newline. Otherwise,
		// the de-serialization on the server-side may become confused with long
		// lines in the data portion of the SOAP message and insert an unwanted
		// newline into the data.
		String digSig = createDigitalSignature(cfg.certKey, cfg.getInputFileWithPath(), true);
		digSig += "\n";

		// Save the digital signature
		if (!writeFile(cfg.getSignatureFileWithPath(), digSig, false)) {
			log.fatal("writeFile() had a problem");
			System.exit(-1);
		}
		RequestSignature reqSig = new RequestSignature();
		reqSig.setRequestSignature(digSig);

		MiWebServiceStub service = null;

		service = new MiWebServiceStub(cfg.endPoint);
		service._getServiceClient().getOptions().setProperty(Constants.Configuration.ENABLE_MTOM, "true");
		service._getServiceClient().getOptions().setTimeOutInMilliSeconds(cfg.timeoutSeconds * 1000);

		/*
		 * service._getServiceClient().getOptions().setProperty(Constants.
		 * Configuration.CACHE_ATTACHMENTS, Constants.VALUE_TRUE);
		 * service._getServiceClient().getOptions().setProperty(Constants.
		 * Configuration.ATTACHMENT_TEMP_DIR,"C:\\backup\\");
		 * service._getServiceClient().getOptions().setProperty(Constants.
		 * Configuration.FILE_SIZE_THRESHOLD, "4000");
		 */

		AttachmentType[] attachmentTypes = new AttachmentType[cfg.attachmentList.size()];
		AttachmentType attachmentType = null;
		int count = 0;
		for (AttachmentInfo attInfo : cfg.attachmentList) {
			digSig = createDigitalSignature(cfg.certKey, cfg.getAttachmentFileWithPath(attInfo.getFileName()), false);
			if (digSig == null) {
				log.fatal("Digital Signature cannot be created");
				System.exit(-1);
			}
			digSig += "\n";
			if (!writeFile(cfg.getAttachSignatureFileWithPath(attInfo.getSignatureFile()), digSig, true)) {
				log.fatal("writeFile() had a problem");
				System.exit(-1);
			}
			attachmentType = new AttachmentType();
			AttachmentSignature sig = new AttachmentSignature();
			sig.setAttachmentSignature(digSig);
			attachmentType.setSignature(sig);
			attachmentType.setDocumentType(attInfo.getType());
			attachmentType.setFileName(attInfo.getFileName());

			// DataSource source = new
			// FileDataSource(cfg.getAttachmentFileWithPath(attInfo.getFileName()));
			DataSource source = new InputStreamDataSource(
					new File(cfg.getAttachmentFileWithPath(attInfo.getFileName())));
			DataHandler dh = new DataHandler(source);
			attachmentType.setBinaryData(dh);
			attachmentTypes[count] = attachmentType;
			count++;
		}

		//
		// Submit attachment
		//
		RequestAttInfo reqInfo = new RequestAttInfo();
		reqInfo.setAdminRole(cfg.adminRole);
		reqInfo.setRequestDataCompressed(false);
		reqInfo.setSendRequestDataOnSuccess(true);
		reqInfo.setSendResponseDataCompressed(false);
		reqInfo.setRequestDataType(cfg.requestDataType);
		reqInfo.setRequestType(RequestType.Factory.fromValue(cfg.requestType));
		reqInfo.setRequestSignature(reqSig);

		DataSource source = new FileDataSource(cfg.getInputFileWithPath());
		DataHandler dh = new DataHandler(source);
		reqInfo.setRequestData(dh);
		reqInfo.setAttachmentData(attachmentTypes);

		System.out.println("Submitting attachment...");
		ResponseAttInfo respAttInfo = null;
		ResponseDataType respDataType = null;

		respAttInfo = service.submitAttachment(reqInfo);
		System.out.println("Submitting attachment returned");

		System.out.println("Success: " + respAttInfo.getSuccess());
		System.out.println("Warning: " + respAttInfo.getWarnings());

		respDataType = respAttInfo.getResponseDataType();

		if (!respDataType.getValue().equalsIgnoreCase(ResponseDataType._XML)) {
			String inputFile = cfg.inputFile;
			int i = inputFile.lastIndexOf(".");
			String file = inputFile.substring(0, i);
			StringBuffer strBuf = new StringBuffer();
			if (respDataType.getValue().equalsIgnoreCase(ResponseDataType._CSV))
				strBuf.append(file).append(cfg.outputFileIdentifier).append(".csv");
			else if (respDataType.getValue().equalsIgnoreCase(ResponseDataType._HTML))
				strBuf.append(file).append(cfg.outputFileIdentifier).append(".csv");
			else if (respDataType.getValue().equalsIgnoreCase(ResponseDataType._JSON))
				strBuf.append(file).append(cfg.outputFileIdentifier).append(".json");
			else if (respDataType.getValue().equalsIgnoreCase(ResponseDataType._TXT))
				strBuf.append(file).append(cfg.outputFileIdentifier).append(".txt");
			cfg.outputFile = strBuf.toString();
		}

		// Write response to file
		dh = respAttInfo.getResponseData();
		try {
			InputStream is = dh.getInputStream();
			FileOutputStream out = new FileOutputStream(new File(cfg.outputDir, cfg.outputFile));
			System.out.println("Writing response to file: " + cfg.getOutputFileWithPath());
			System.out.println("Writing file: " + cfg.outputFile);
			try {
				byte buf[] = new byte[1024];
				for (;;) {
					int numBytesRead = is.read(buf);
					out.write(buf, 0, numBytesRead);
					if (numBytesRead < buf.length)
						break;
				}
			} finally {
				out.close();
			}
			// AttachmentReferenceType [] attachReferences =
			// respAttInfo.getAttachmentReferences();
			// if (attachReferences != null)
			// {
			// for (AttachmentReferenceType attachReference : attachReferences)
			// {
			// System.out.println("Attachment Id: " +
			// attachReference.getDocumentId());
			// System.out.println("Attachment Type: " +
			// attachReference.getDocumentType());
			// System.out.println("Attachment FileName: " +
			// attachReference.getFileName());
			// }
			// }
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		catch (Exception ex) {
		  ex.printStackTrace();
		}

		System.out.println("Finished client.");
	}

	/**
	 * Set security properties in the JVM environment.
	 *
	 * @param trustStore
	 *            JKS trust store, which has the CA certificate.
	 * @param trustStorePassword
	 *            JKS trust store password.
	 * @return true if successful
	 */
	public static boolean setSecurityProps(String trustStore, String trustStorePassword) {
		// Set the security credential properties
		try {
			System.out.println("trustStore " + trustStore);
			System.out.println("trustStorePassword " + trustStorePassword);
			// Set CA cert trust store props
			System.setProperty("javax.net.ssl.trustStore", trustStore);
			System.setProperty("javax.net.ssl.trustStoreType", "JKS");
			System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
			// System.out.println("trustStore: " + trustStore);
		} catch (Exception ex) {
			System.out.println("Exception occurred in setSecurityProps: " + ex.toString());
			return false;
		}
		return true;
	}

	/*
	 * fetchKey
	 */
	/**
	 * Fetch the key from the client MP certificate.
	 *
	 * @param clientCert
	 *            Client MP certificate name
	 * @param clientCertPassword
	 *            Client MP certificate password to get the key
	 * @return
	 */
	public static Key fetchKey(String clientCert, String clientCertPassword) {
		Key key = null;

		// Read the certificate into memory
		try {
			// Set client cert props
			System.setProperty("javax.net.ssl.keyStore", clientCert);
			System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
			System.setProperty("javax.net.ssl.keyStorePassword", clientCertPassword);
			System.out.println("ClientCert: " + clientCert);

			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(new FileInputStream(clientCert), clientCertPassword.toCharArray());
			String keyAlias = null;
			for (Enumeration<String> e = ks.aliases(); e.hasMoreElements();) {
				keyAlias = e.nextElement();
			}
			key = ks.getKey(keyAlias, clientCertPassword.toCharArray());
		} catch (Exception ex) {
			log.fatal("Exception occurred in fetchKey: " + ex.toString());
			System.exit(-1);
		}

		return key;
	}

	/*
	 * readFile - read text file
	 */
	/**
	 * Loads the client request file.
	 *
	 * @param fileName
	 *            name of the client request file.
	 *
	 * @return the file contents as byte array.
	 */
	public static byte[] readFile(File file) {

		try {
			if ((file.exists() == false) || (file.isFile() == false) || !file.canRead()) {
				return null;
			}

			// Read file contents
			InputStream is = new FileInputStream(file);
			byte[] ba = new byte[is.available()];
			is.read(ba);
			is.close();

			// Return contents
			return ba;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Write the response obtained from the server into a file. It can handle
	 * both binary and text data. The binary data has to be Base 64 encoded.
	 *
	 * @param fileName
	 *            name of the file to write to.
	 * @param data
	 *            contents of the file.
	 * @param binaryFlag
	 *            flag to indicate if the file contents is binary.
	 *
	 * @return true if successful.
	 */
	public static boolean writeFile(String fileName, String data, boolean binaryFlag) {
		System.out.println("Writing file: " + fileName);

		// Write text or binary data based on binaryFlag
		try {
			if (!binaryFlag) {
				BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
				br.write(data, 0, data.length());
				br.flush();
				br.close();
				return true;
			} else {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));
				BASE64Decoder b64d = new BASE64Decoder();
				byte[] binaryData = b64d.decodeBuffer(data);
				bos.write(binaryData, 0, binaryData.length);
				bos.flush();
				bos.close();
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Creates digital signature for the data using client certificate key.
	 *
	 * @param key
	 *            client certificate key
	 * @param data
	 *            data to be signed. It has to be XML data for this project.
	 *
	 * @return digital signature for the data payload.
	 */
	public static String createDigitalSignature(Key key, byte[] data, boolean isXML) {

		byte[] signature = null;
		byte[] updateBytes = null;

		try {
			// Initialize xml-security library
			org.apache.xml.security.Init.init();

			if (isXML) {
				// Build DOM document from XML data
				DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
				dfactory.setNamespaceAware(true);
				dfactory.setValidating(true);
				DocumentBuilder documentBuilder = dfactory.newDocumentBuilder();
				// This is to throw away all validation errors
				documentBuilder.setErrorHandler(new org.apache.xml.security.utils.IgnoreAllErrorHandler());
				Document doc = documentBuilder.parse(new ByteArrayInputStream(data));

				// Build canonicalized XML from document
				Canonicalizer c14n = Canonicalizer
						.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
				updateBytes = c14n.canonicalizeSubtree(doc);
			} else {
				updateBytes = data;
			}

			// Initialize signing object with SHA1 digest and RSA encryption
			Signature rsa = Signature.getInstance("SHA256withRSA");

			// Set private key into signing object
			rsa.initSign((PrivateKey) key);

			// Generate signature
			rsa.update(updateBytes);
			signature = rsa.sign();
		} catch (Exception ex) {
			log.fatal("Exception occurred in createDigitalSignature (byte array): " + ex.toString());
			System.exit(-1);
		}

		// Base64 encode signature
		BASE64Encoder b64e = new BASE64Encoder();
		String signatureString = b64e.encode(signature);

		return signatureString;
	}

	/**
	 * Creates digital signature for the data using client certificate key.
	 *
	 * @param key
	 *            client certificate key
	 * @param filePath
	 *            data to be signed.
	 * @isXML indicates if the file is an XML payload. XML payloads are
	 *        processed with the assumption of limited size and hence will use
	 *        the byte array version of the API call. Other types of file will
	 *        use the stream api to accomodate huge files
	 * @return digital signature for the data payload.
	 */
	public static String createDigitalSignature(Key key, String filePath, boolean isXML) {
		File file = new File(filePath);
		FileInputStream fis = null;
		byte[] signature = null;
		try {
			if (file.exists() && file.isFile()) {
				if (isXML) {
					// nu.xom.canonical.Canonicalizer c = new
					// nu.xom.canonical.Canonicalizer()
					return createDigitalSignature(key, readFile(file), true);
				}
				Signature rsa = Signature.getInstance("SHA256withRSA");
				// Set private key into signing object
				rsa.initSign((PrivateKey) key);
				fis = new FileInputStream(file);
				BufferedInputStream bufin = new BufferedInputStream(fis);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = bufin.read(buffer)) >= 0) {
					rsa.update(buffer, 0, len);
				}
				bufin.close();
				// Generate signature
				signature = rsa.sign();
				// Base64 encode signature
				BASE64Encoder b64e = new BASE64Encoder();
				String signatureString = b64e.encode(signature);
				return signatureString;
			}
		} catch (Exception ex) {
			System.out.println("Exception occurred in createDigitalSignature (stream): " + ex.toString());
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Download the attachment from the server
	 * 
	 * @param cfg
	 */
	private static void retrieveAttachment(ConfigParameters cfg) {
		System.out.println("Retrieve Attachment");
		AttachmentReferenceType attachReference = new AttachmentReferenceType();
		attachReference.setDocumentId(cfg.attachmentId);
		attachReference.setDocumentMimeType("application/pdf");
		attachReference.setDocumentType("legal");
		attachReference.setFileName("file1.txt");
		AttachmentReferenceInfo referenceInfo = new AttachmentReferenceInfo();
		referenceInfo.setAttachmentReferenceInfo(attachReference);

		FileOutputStream out = null;

		try {
			MiWebServiceStub service = new MiWebServiceStub(cfg.endPoint);
			service._getServiceClient().getOptions().setProperty(Constants.Configuration.ENABLE_MTOM, "true");
			service._getServiceClient().getOptions().setTimeOutInMilliSeconds(300 * 1000);

			com.abb.sable.mi.ws_axis.AttachmentInfo attachmentInfo = service.retrieveAttachment(referenceInfo);
			AttachmentType attachType = attachmentInfo.getAttachmentInfo();

			if (attachType.getSuccess()) {
				System.out.println(attachType.getDocumentType());
				System.out.println(attachType.getDocumentMimeType());
				System.out.println(attachType.getFileName());
				DataHandler dh = attachType.getBinaryData();
				InputStream is = dh.getInputStream();
				out = new FileOutputStream(new File(cfg.outputDir, attachType.getFileName()));
				byte buf[] = new byte[1024];
				for (;;) {
					int numBytesRead = is.read(buf);
					out.write(buf, 0, numBytesRead);
					if (numBytesRead < buf.length)
						break;
				}
				System.out.println("Successfully Downloaded Attachment");
			} else {
				System.out.println("Unable to Download Attachment");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return;
	}

	/**
	 * Delete the attachment from the server
	 * 
	 * @param cfg
	 */
	private static void removeAttachment(ConfigParameters cfg) {
		System.out.println("Remove Attachment");
		AttachmentReferenceType attachReference = new AttachmentReferenceType();
		attachReference.setDocumentId(cfg.attachmentId);
		attachReference.setDocumentMimeType("application/pdf");
		attachReference.setDocumentType("legal");
		attachReference.setFileName("file1.txt");
		AttachmentReferenceInfo referenceInfo = new AttachmentReferenceInfo();
		referenceInfo.setAttachmentReferenceInfo(attachReference);

		try {
			MiWebServiceStub service = new MiWebServiceStub(cfg.endPoint);
			service._getServiceClient().getOptions().setProperty(Constants.Configuration.ENABLE_MTOM, "true");
			service._getServiceClient().getOptions().setTimeOutInMilliSeconds(300 * 1000);
			com.abb.sable.mi.ws_axis.AttachmentRemovalResponse attachmentRemovalResponse = service
					.removeAttachment(referenceInfo);
			if (attachmentRemovalResponse.getAttachmentRemovalResponse()) {
				System.out.println("Successfully removed the Attachment");
			} else {
				System.out.println("Unable to Remove the Attachment");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}

		return;
	}

	private static Logger log = Logger.getLogger(MiWebServiceClientNew.class.getName());

	/**
	 * Configuration parameters class
	 */
	public static class ConfigParameters {
		/**
		 * Constructor which loads the info from configuration file.
		 */
		private ConfigParameters(byte[] fileContents, String inputFilePath, String certificatePath) {
			// Default values
			this.proxySet = false;
			this.outputFileIdentifier = "-out";
			this.signatureFileIdentifier = "-sig";
			this.inputDir = "./request-files";
			this.outputDir = "./response-files";
			// System.out.println(new String(fileContents));
			try {
				InputStream is = new ByteArrayInputStream(fileContents);
				BufferedReader in = new BufferedReader(new InputStreamReader(is));

				String str = null;
				String str2 = null;

				String certFileWithPath = null;
				String prevCertFileWithPath = null;

				String setStr = "\\s*=\\s*";
				int attachmentCount = 0;

				while ((str = in.readLine()) != null) {
					str2 = str.trim();

					if (str2.startsWith("#"))
						continue;

					if (str2.length() == 0)
						continue;
					System.out.println(str2);

					String[] fields = str2.split(setStr);
					if (fields.length >= 2) {
						String key = fields[0];
						String value = fields[1];
						if (key.equals(PROXY_SET)) {
							if (value.equals("true"))
								this.proxySet = true;
						} else if (key.equals(PROXY_HOST)) {
							this.proxyHost = value;
						} else if (key.equals(PROXY_PORT)) {
							this.proxyPort = value;
						} else if (key.equals(END_POINT)) {
							this.endPoint = value;
						} else if (key.equals(TRUST_STORE)) {
							this.trustStore = value;
						} else if (key.equals(TRUST_STORE_PASSWORD)) {
							this.trustStorePassword = value;
						} else if (key.equals(OUTPUT_FILE_IDENT)) {
							this.outputFileIdentifier = value;
						} else if (key.equals(SIGNATURE_FILE_IDENT)) {
							this.signatureFileIdentifier = value;
						} else if (key.equals(TIMEOUT_SECONDS)) {
							this.timeoutSeconds = Integer.parseInt(value);
						} else if (key.equals(CERT_DIR)) {
							this.certDir = value;
						} else if (key.equals(CERT_FILE)) {
							this.certFile = value;
						} else if (key.equals(CERT_PASSWORD)) {
							this.certPassword = value;
						} else if (key.equals(ADMIN_ROLE)) {
							if (value.equals("true"))
								this.adminRole = true;
						} else if (key.equals(INPUT_DIR)) {
							this.inputDir = value;
						} else if (key.equals(OUTPUT_DIR)) {
							this.outputDir = value;
						} else if (key.equals(REQUEST_TYPE)) {
							this.requestType = value;
						} else if (key.equals(REQUEST_DATA_TYPE)) {
							RequestDataType type = RequestDataType.Factory.fromValue(value);
							this.requestDataType = type;
						} else if (key.equals(INPUT_FILE)) {
							this.inputFile = inputFilePath;

							int i = inputFile.lastIndexOf(".");
							String file = inputFile.substring(0, i);
							StringBuffer strBuf = new StringBuffer();
							strBuf.append(file).append(this.outputFileIdentifier).append(".xml");
							this.outputFile = strBuf.toString();
							System.out.println("outputFile " + outputFile);

							// strBuf.delete(0, str.length());
							strBuf = new StringBuffer();
							strBuf.append(file).append(this.signatureFileIdentifier).append(".txt");
							this.signatureFile = strBuf.toString();
							System.out.println("signatureFile " + signatureFile);

							StringBuffer strBuf2 = new StringBuffer(this.certDir);
							strBuf2.append("/").append(this.certFile);
							certFileWithPath = certificatePath;
							System.out.println("certificatePath" + certificatePath);
							if (this.certKey == null) {
								// Only first time will be null
								this.certKey = fetchKey(certFileWithPath, this.certPassword);
							} else {
								if (prevCertFileWithPath != null && certFileWithPath.equals(prevCertFileWithPath)) {
									// Use previous certKey
								} else {
									this.certKey = fetchKey(certFileWithPath, this.certPassword);
								}
							}
							prevCertFileWithPath = certFileWithPath;
							System.out.println("Input File " + this.inputFile);
							System.out.println("Output File " + this.outputFile);
							System.out.println("Signature File " + this.signatureFile);
						} else if (key.equals(ATTACHMENT_OPERATION)) {
							this.attachmentOperation = value;
						} else if (key.equals(ATTACHMENT_DIR)) {
							this.attachmentDir = value;
						} else if (key.equals(QUERY_ATTACHMENT_ID)) {
							this.attachmentId = value;
						} else if (key.equals(ATTACHMENT_FILES)) {
							attachmentCount++;
							AttachmentInfo attInfo = new AttachmentInfo();
							String info[] = value.split(",");

							attInfo.setId(attachmentCount + "");
							attInfo.setType(info[0]);
							attInfo.setFileName(info[1]);
							attInfo.setSignatureFile(info[1] + "-sig.txt");

							attachmentList.add(attInfo);

							if (this.certKey == null) {
								// Only first time will be null
								this.certKey = fetchKey(certFileWithPath, this.certPassword);
							} else {
								if (prevCertFileWithPath != null && certFileWithPath.equals(prevCertFileWithPath)) {
									// Use previous certKey
								} else {
									this.certKey = fetchKey(certFileWithPath, this.certPassword);
								}
							}
							prevCertFileWithPath = certFileWithPath;
						}
					} else {
						System.out.println("Parse error: " + str2);
					}
				}
				in.close();

				if (this.proxySet) {
					System.out.println("ProxySet: " + this.proxySet);
					System.out.println("ProxyHost: " + this.proxyHost);
					System.out.println("ProxyPort: " + this.proxyPort);
				}
				System.out.println("EndPoint: " + this.endPoint);
				System.out.println("Keystore: " + this.trustStore);
			} catch (IOException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			} catch (Exception ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
		}

		private String getInputFileWithPath() {
			StringBuffer str = new StringBuffer(inputDir);
			str.append("/").append(inputFile);
			return (inputFile);
		}

		private String getAttachmentFileWithPath(String attachmentFile) {
			System.out.println(attachmentDir);
			StringBuffer str = new StringBuffer(attachmentDir);
			str.append("/").append(attachmentFile);
			System.out.println(attachmentFile);
			return (attachmentFile);
		}

		private String getOutputFileWithPath() {
			StringBuffer str = new StringBuffer(outputDir);
			str.append("/").append(outputFile);
			return (outputFile);
		}

		private String getSignatureFileWithPath() {
			StringBuffer str = new StringBuffer(outputDir);
			str.append("/").append(signatureFile);
			return (signatureFile);
		}

		private String getAttachSignatureFileWithPath(String signatureFile) {
			StringBuffer str = new StringBuffer(attachmentDir);
			str.append("/").append(signatureFile);
			return (signatureFile);
		}

		private static final String PROXY_SET = "ws.client.proxy.proxySet";
		private static final String PROXY_HOST = "ws.client.proxy.proxyHost";
		private static final String PROXY_PORT = "ws.client.proxy.proxyPort";
		private static final String END_POINT = "ws.client.end.point";
		private static final String TRUST_STORE = "ws.client.server.cert.truststore";
		private static final String TRUST_STORE_PASSWORD = "ws.client.truststore.password";
		private static final String OUTPUT_FILE_IDENT = "ws.client.output.file.identifier";
		private static final String SIGNATURE_FILE_IDENT = "ws.client.signature.file.identifier";
		private static final String TIMEOUT_SECONDS = "ws.client.timeout.in.seconds";

		private static final String CERT_DIR = "ws.client.cert.dir";
		private static final String CERT_FILE = "ws.client.cert.file";
		private static final String CERT_PASSWORD = "ws.client.cert.password";
		private static final String ADMIN_ROLE = "ws.client.admin.role";
		private static final String INPUT_DIR = "ws.client.input.dir";
		private static final String OUTPUT_DIR = "ws.client.output.dir";
		private static final String REQUEST_TYPE = "ws.client.request.type";
		private static final String REQUEST_DATA_TYPE = "ws.client.request.data.type";
		private static final String INPUT_FILE = "ws.client.input.file";

		private static final String ATTACHMENT_FILES = "ws.client.attachment";
		private static final String ATTACHMENT_DIR = "ws.client.attachment.dir";
		private static final String QUERY_ATTACHMENT_ID = "ws.client.attachment.query.id";
		private static final String ATTACHMENT_OPERATION = "ws.client.attachment.operation";

		// Global Parameters
		private boolean proxySet;
		private String proxyHost;
		private String proxyPort;
		private String endPoint;
		private String trustStore;
		private String trustStorePassword;
		private String outputFileIdentifier;
		private String signatureFileIdentifier;
		private int timeoutSeconds = 60;

		// Request Parameters
		private String certDir;
		private String certFile;
		private String certPassword;
		private boolean adminRole;
		private String inputDir;
		private String outputDir;
		private String requestType;
		private RequestDataType requestDataType;
		private String inputFile;
		private String outputFile;
		private String signatureFile;
		private Key certKey;

		private List<AttachmentInfo> attachmentList = new ArrayList<AttachmentInfo>();
		private String attachmentOperation;
		private String attachmentDir;
		private String attachmentId;
	}
}
