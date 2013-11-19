package com.philipp.tools.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.philipp.tools.common.log.Logger;
import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPAddressSucceededException;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPTransport;

public class MailNotifier {
	
	private static JCommander commander;
	
	@Parameter(names = { "-v", "-verbose"}, description = "STD.ERR logging on/off") 
	private boolean verbose = false;
	
	@Parameter(names = { "-m", "-mailhost"}, description = "Transport-host", required = true) 
	private String mailhost;
	
	@Parameter(names = "-ssl", description = "SSL on") 
	private boolean ssl = false;
	
	@Parameter(names = { "-u", "-user"}, description = "Username", required = true) 
	private String user;
	
	@Parameter(names = { "-p", "-password"}, description = "Password", required = true) 
	private String password;
	
	@Parameter(names = { "-from", "-sender"}, description = "Message sender", required = true) 
	private String sender;
	
	@Parameter(names = { "-to", "-recipients"}, description = "Message recipient list separated by commas", required = true) 
	private String recipients;
	
	@Parameter(names = { "-a", "-attach"}, description = "File list to attach separated by ;") 
	private String attach;
	
	@Parameter(names = {"-subject", "-title"}, description = "Message subject") 
	private String subject = "MailNotifier message";
	
	@Parameter(names = {"-help", "-?"}, help = true, hidden = true) 
	private boolean help; 
	
	private static final String MAILER = "MailNotifier";
	private static final String PROTOCOL = "smtp";
	private static final String SSL_PROTOCOL = "smtps";
	
	private MailNotifier (String[] args) throws ParameterException  {
		commander = new JCommander(this, args);	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			MailNotifier manager = new MailNotifier(args);

			if (manager.help) {
				commander.usage();
				System.exit(0);
				return;
			}
			
			if (manager.verbose) {
				Logger.DEBUG_ON = true;
			}
			
			manager.sendMail(collect());	
			System.exit(0);
		}
		catch (SMTPSendFailedException e) {
			Logger.err(e);
			logInfo(e);
			System.exit(1);
			return;
		}		
		catch (Exception e) {
			Logger.err(e);			
			System.exit(1);
			return;
		}

	}
	
	private void sendMail (String text) throws AddressException, SMTPSendFailedException, MessagingException, IOException {
			
		Properties props = System.getProperties();		  
		props.put("mail." + (ssl ? SSL_PROTOCOL : PROTOCOL) + ".host", mailhost);		
		props.put("mail." + (ssl ? SSL_PROTOCOL : PROTOCOL) + ".auth", "true");
		
		Session session = Session.getInstance(props, null);		    
		session.setDebug(verbose);
		
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(sender));		
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients, false));
		
		msg.setSubject(subject);
		msg.setHeader("X-Mailer", MAILER);
		msg.setSentDate(new Date());
		
		if (attach != null) {
			MimeMultipart mp = new MimeMultipart();
			MimeBodyPart mbpText = new MimeBodyPart();
			mbpText.setText(text);
			mp.addBodyPart(mbpText);
			
			String[] files = attach.split(";");
			for (String file : files) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				mbpFile.attachFile(file);	
				mp.addBodyPart(mbpFile);
			}
			msg.setContent(mp);			
		}
		else {
			msg.setText(text);
		}
		
		SMTPTransport t = (SMTPTransport)session.getTransport(ssl ? SSL_PROTOCOL : PROTOCOL);
		
		try {
			t.connect(mailhost, user, password);		
			t.sendMessage(msg, msg.getAllRecipients());
		}
		finally {
			Logger.debug(t.getLastServerResponse());
			t.close();
		}
		
	}
	
	private static String collect () throws IOException {
		
		BufferedReader scan =
				new BufferedReader(new InputStreamReader(System.in, Logger.FILE_ENCODING));
		
		String line;
		StringBuffer sb = new StringBuffer();	
		
		while ((line = scan.readLine()) != null) {
		    sb.append(line);
		    sb.append("\n");		  
		}		
		return sb.toString();
	}
	
	private static void logInfo (SMTPSendFailedException e) {
		
		 Logger.err("SMTP SEND FAILED:");
		 Logger.err("\tCommand:  \t" + e.getCommand());
		 Logger.err("\tRetCode:  \t" + e.getReturnCode());
		 Logger.err("\tResponse: \t" + e.getMessage());
		 		 		 
		 Exception chain = e.getNextException();
		 while (chain != null) {
			if (chain instanceof SMTPAddressFailedException) {
				Logger.err("ADDRESS FAILED:");				
				Logger.err("\tAddress:  \t" + ((SMTPAddressFailedException)chain).getAddress());
				Logger.err("\tCommand:  \t" + ((SMTPAddressFailedException)chain).getCommand());
				Logger.err("\tRetCode:  \t" + ((SMTPAddressFailedException)chain).getReturnCode());
				Logger.err("\tResponse: \t" + ((SMTPAddressFailedException)chain).getMessage());
				chain =  ((SMTPAddressFailedException)chain).getNextException();
			}
			else if (chain instanceof SMTPAddressSucceededException) {
				Logger.err("ADDRESS SUCCEEDED:");
				Logger.err("\tAddress:  \t" + ((SMTPAddressSucceededException)chain).getAddress());
				Logger.err("\tCommand:  \t" + ((SMTPAddressSucceededException)chain).getCommand());
				Logger.err("\tRetCode:  \t" + ((SMTPAddressSucceededException)chain).getReturnCode());
				Logger.err("\tResponse: \t" + ((SMTPAddressSucceededException)chain).getMessage());
				chain =  ((SMTPAddressSucceededException)chain).getNextException();
			}
		 }		 		
	}

}
