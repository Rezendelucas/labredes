package criptografiasimetrica;


import java.io.*;
import java.net.*;

class TCPServerAES {

    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        String newString[] = new String[1];
        String secretKey = "secretKey";
        String hash;
        String newHash;

        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {

            Socket connectionSocket = welcomeSocket.accept();

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();

            System.out.println("FROM CLIENT: " + clientSentence);

            newString = clientSentence.split(";");
            
            hash = AES.decrypt(newString[0], secretKey);
            
            //System.out.println(hash);

            if (hash.equals(newString[1])) {

                capitalizedSentence = newString[1].toUpperCase();
    
                System.out.println(capitalizedSentence);
                
                outToClient.writeBytes(AES.encrypt(capitalizedSentence, secretKey) + ";" + capitalizedSentence + '\n');

            }
        }
    }
}
