package com.galaxii.common.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see http://www.sk-jp.com/book/javamail/contents/javamail_standard.html
 */
public class SimpleMailSender {

	protected static Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);

	private static String ENCODE = "ISO-2022-JP";

    private Session session;
    private Transport transport;

	private String host = "localhost";
	private int port = 25;
	private String user;
	private String password; // transient
	private String fromDomain;

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setFromDomain(String fromDomain) {
		this.fromDomain = fromDomain;
	}
	
	public String getFromDomain() {
		return fromDomain;
	}

    public SimpleMailSender() {
        this(new Properties());
    }

    public SimpleMailSender(Properties prop) {
        this(prop, "smtp");
    }

    public SimpleMailSender(Properties prop, String protocol) {
		//throws NoSuchProviderException {

        session = Session.getInstance(prop, null);

		try {
			transport = session.getTransport(protocol);
		} catch (NoSuchProviderException e) {
			// NoSuchProviderExceptionは通常でないはずなので
			//throw new Exception();
		}
    }

    public synchronized void connect() throws MessagingException {
        transport.connect(host, port, user, password);
	}

/*
    public synchronized void connect(String host,
								     int port,
                                     String user,
                                     String pass) throws MessagingException {
        transport.connect(host, port, user, pass);
	}
*/
    public synchronized void disconnect() {
        try {
            transport.close();
        } catch (MessagingException e) {}
    }

    public void send(MimeMessage msg) throws MessagingException {
        send(msg, msg.getAllRecipients());
    }

    public void send(MimeMessage msg, Address[] recipients)
                throws MessagingException {
        msg.setSentDate(new Date());
        // Message-ID:にFromアドレスを使用します。
        session.getProperties().put("mail.from",
                ((InternetAddress)msg.getFrom()[0]).getAddress());
        msg.saveChanges();
        transport.sendMessage(msg, recipients);
    }

    public void send(String subject, String body,
                     String to, String from)
                throws MessagingException {
        MimeMessage msg = createMessage();

		setHeaders(msg, to, from);

        msg.setSubject(subject, ENCODE);
        msg.setText(body, ENCODE);

        send(msg);
    }

    public void send(MimeMessage msg,
                     String envelopeTo,
                     String envelopeFrom)
                throws MessagingException, AddressException {

        session.getProperties().put("mail.smtp.from", envelopeFrom);
        send(msg, InternetAddress.parse(envelopeTo, true));
        session.getProperties().remove("mail.smtp.from");
    }

    public static void setHeaders(MimeMessage msg, String to, String from)
                throws MessagingException, AddressException {
        msg.setFrom(null);
        msg.addFrom(InternetAddress.parse(from, true));
        msg.setRecipients(Message.RecipientType.TO,
                          InternetAddress.parse(to, true));
        msg.setHeader("X-Mailer", "JavaMail Sender"); // 適当な名前にしてください
    }

    public void useSMTPAuth(boolean auth) {
        session.getProperties().put("mail.smtp.auth", String.valueOf(auth));
    }

    public MimeMessage createMessage() {
        return new MimeMessage(session);
    }

}
