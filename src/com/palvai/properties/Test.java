package com.palvai.properties;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test {

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
	private String inputFile;
	private String outputFile;
	private String signatureFile;
	byte dataBuffer[];
	
	public static void main(String args[]) {
		Test obj = new Test();
	  obj.ConfigParameters("sdsd");	
	}

	public Test() {

		File file = new File("J:\\workspace\\iv\\demo\\src\\ws-client.properties");
		int len = 1024;
		if (file.exists()) {
			len = (int) file.length();
		}
		this.dataBuffer = new byte[len];
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			while (in.read(dataBuffer, 0, len) != -1)
				;
			
			in.close();

		} catch (IOException e) {
			// handle exception
		}
		System.out.println(new String(dataBuffer));

	}

	private void ConfigParameters(String fileName) {
		// Default values
		this.proxySet = false;
		this.outputFileIdentifier = "-out";
		this.signatureFileIdentifier = "-sig";
		this.inputDir = "./request-files";
		this.outputDir = "./response-files";

		try {
			InputStream is = new ByteArrayInputStream(this.dataBuffer);
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
						System.out.println("Cert Dir " + this.certDir);
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
						// RequestDataType type = RequestDataType.Factory.fromValue(value);
						// this.requestDataType = type;
					} else if (key.equals(INPUT_FILE)) {
						this.inputFile = value;

						int i = inputFile.lastIndexOf(".");
						String file = inputFile.substring(0, i);
						StringBuffer strBuf = new StringBuffer();
						strBuf.append(file).append(this.outputFileIdentifier).append(".xml");
						this.outputFile = strBuf.toString();

						strBuf.delete(0, str.length());
						strBuf.append(file).append(this.signatureFileIdentifier).append(".txt");
						this.signatureFile = strBuf.toString();

						StringBuffer strBuf2 = new StringBuffer(this.certDir);
						strBuf2.append("/").append(this.certFile);
						certFileWithPath = strBuf2.toString();
						/*
						 * if (this.certKey == null) { // Only first time will be null //this.certKey =
						 * fetchKey(certFileWithPath, this.certPassword); } else { if
						 * (prevCertFileWithPath != null &&
						 * certFileWithPath.equals(prevCertFileWithPath)) { // Use previous certKey }
						 * else { //this.certKey = fetchKey(certFileWithPath, this.certPassword); } }
						 */
						prevCertFileWithPath = certFileWithPath;
					} else if (key.equals(ATTACHMENT_OPERATION)) {
						// this.attachmentOperation = value;
					} else if (key.equals(ATTACHMENT_DIR)) {
						// this.attachmentDir = value;
					} else if (key.equals(QUERY_ATTACHMENT_ID)) {
						// this.attachmentId = value;
					} else if (key.equals(ATTACHMENT_FILES)) {
						attachmentCount++;
						/*
						 * AttachmentInfo attInfo = new AttachmentInfo(); String info[] =
						 * value.split(",");
						 * 
						 * attInfo.setId(attachmentCount + ""); attInfo.setType(info[0]);
						 * attInfo.setFileName(info[1]); attInfo.setSignatureFile(info[1] + "-sig.txt");
						 * 
						 * attachmentList.add(attInfo);
						 */

						/*
						 * if (this.certKey == null) { // Only first time will be null //this.certKey =
						 * fetchKey(certFileWithPath, this.certPassword); } else { if
						 * (prevCertFileWithPath != null &&
						 * certFileWithPath.equals(prevCertFileWithPath)) { // Use previous certKey }
						 * else { //this.certKey = fetchKey(certFileWithPath, this.certPassword); } }
						 */
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
		}
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

}
