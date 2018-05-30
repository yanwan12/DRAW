package com.yanwann.drawsrv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serveur {

	
	public static String msgClient;
	public static String msgServeur;
	
 
   public static String getMsgClient() {
		return msgClient;
	}

	public static void setMsgClient(String msgClient) {
		Serveur.msgClient = msgClient;
	}

	public static String getMsgServeur() {
		return msgServeur;
	}

	public static void setMsgServeur(String msgServeur) {
		Serveur.msgServeur = msgServeur;
	}

public static void main(String[] test) {
  
     final ServerSocket serveurSocket  ;
     final Socket clientSocket ;
     final BufferedReader in;
     final PrintWriter out;
     final Scanner sc=new Scanner(System.in);
  
     try {
       serveurSocket = new ServerSocket(5300);
       clientSocket = serveurSocket.accept();
       out = new PrintWriter(clientSocket.getOutputStream());
       in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
       Thread envoi= new Thread(new Runnable() {
          //public String msg;
          @Override
          public void run() {
             while(true){
                msgServeur = sc.nextLine();
                out.println(msgServeur);
                out.flush();
             }
          }
       });
       envoi.start();
   
       Thread recevoir= new Thread(new Runnable() {
          //String msg ;
          @Override
          public void run() {
             try {
                msgClient = in.readLine();
                //tant que le client est connecté
                while(msgClient!=null){
                   System.out.println("Client : "+msgClient);
                   msgClient = in.readLine();
                   
                }
                //sortir de la boucle si le client a déconecté
                System.out.println("Client déconecté");
                //fermer le flux et la session socket
                out.close();
                clientSocket.close();
                serveurSocket.close();
             } catch (IOException e) {
                  e.printStackTrace();
             }
         }
      });
      recevoir.start();
      }catch (IOException e) {
         e.printStackTrace();
      }
   }
}
