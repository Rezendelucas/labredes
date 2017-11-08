package criptografiasimetrica;


import java.io.*;
import java.net.*;

class TCPClientAES {

    public static void main(String argv[]) throws Exception {
        String sentence;
        String newString[] = new String[1];
        String secretKey = "secretKey";
        String hash;
        String modifiedSentence;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        sentence = inFromUser.readLine();

        outToServer.writeBytes(AES.encrypt(sentence, secretKey) + ";" + sentence + '\n');

        modifiedSentence = inFromServer.readLine();

        newString = modifiedSentence.split(";");
        System.out.println(modifiedSentence);
        
        System.out.println(newString[0] + " " + newString[1]);
        hash = AES.decrypt(newString[0], secretKey);
        
        
        if (hash.equals(newString[1])) {
            System.out.println("FROM SERVER: " + newString[1]);
        } else {
            System.out.println("Mensagem adulterada!");
        }

        clientSocket.close();

    }
}
