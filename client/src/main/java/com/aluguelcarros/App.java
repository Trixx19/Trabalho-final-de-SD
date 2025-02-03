package com.aluguelcarros;

import java.util.Scanner;
import java.util.List;

public class App {

    public static Boolean inLooping = true;
    public static Scanner sc = new Scanner(System.in);
    public static AluguelCarrosProxy reservation = new AluguelCarrosProxy();
    
    public static void printMenu(){
        System.out.println("\n\033[1;36m🚗  Sistema de Aluguel de Carros 🚗\033[0m");
        System.out.println("\033[1;32m1️⃣  - Listar carros disponíveis\033[0m");
        System.out.println("\033[1;32m2️⃣  - Buscar carros\033[0m");
        System.out.println("\033[1;32m3️⃣  - Adicionar carro\033[0m");
        System.out.println("\033[1;32m4️⃣  - Remover carro\033[0m");
        System.out.println("\033[1;32m5️⃣  - Alugar carro\033[0m");
        System.out.println("\033[1;32m6️⃣  - Encerrar aluguel de carro\033[0m");
        System.out.println("\033[1;32m7️⃣  - Sair\033[0m");
        System.out.print("\n\033[1;36m🎯 Escolha uma opção: \033[0m\n");
        System.out.print(">> ");
    }

    private static void printCarro(Carro carro){
        System.out.println(carro.getModeloPlaca());
        System.out.println("ID: " + carro.get());
        System.out.println("Valor diária: " + carro.getValorDiaria());
        System.out.println("Ano: " + carro.getAno());
        System.out.println("Descrição: " + carro.getdescricao());
        System.out.print("Status: ");

        if (carro.ocupado()){
            System.out.println("Alugado");
        } else {
            System.out.println("Não alugado");
        }
        System.out.println();
    }  

    public static void interpretInput(String operation){
        System.out.println();
        switch (operation) {    
            case "1":
                try{
                    List<Carro> allCarros = reservation.getAllCarros();

                    System.out.println("🚙 LISTAR CARROS");
                    for (Carro carro : allCarros){
                        printCarro(carro);
                    }


                } catch (CarroNotExistsException err) {
                    System.out.println("🚫 Não há carros cadastrados no sistema.");
                }
                
                break;
            case "2":
                System.out.println("🔍 BUSCAR CARRO");
                /*System.out.print("Qual o modelo? ");
                String sctNumber = sc.next();*/
                System.out.print("Qual a placa? \n");
                String pl = sc.next();

                try{
                    Carro result = reservation.searchCarro(/*sctNumber , */ pl);
                    System.out.print("✅ Encontrado: \n");
                    printCarro(result);
                } catch (CarroNotExistsException err) {
                    System.out.println("❌ O carro buscado não existe.");
                }
                
                break;

            case "3":
            System.out.println("➕ ADICIONAR CARRO");
                System.out.print("Modelo do carro ");
                String modelo = sc.next();
                System.out.print("Placa do carro: ");
                String placa = sc.next();
                System.out.print("Ano do carro: ");
                Integer ano = sc.nextInt();
                System.out.print("Valor da diária: ");
                Integer valorDiaria = sc.nextInt(); sc.nextLine();
                System.out.print("Descrição: ");
                String descricao = sc.nextLine();
                
                Carro newCarro = new Carro(descricao, placa, modelo, ano, valorDiaria);
                Boolean result = reservation.addCarro(newCarro);
                
                if (result){
                    System.out.println("✅ Carro adicionado com sucesso");
                } else {
                    System.out.println("🚫 Não foi possivel adicionar");
                }

                break;

            case "4":
                System.out.println("❌ REMOVER CARRO");
                System.out.print(" : ID? ");
                Integer ID = sc.nextInt();
                
                try{
                    Carro removedCarro = reservation.removeCarro(ID);
                    System.out.println("✅ O seguinte carro foi removido com sucesso:");
                    printCarro(removedCarro);
                } catch (CarroNotExistsException err) {
                    System.out.println("🚫 O carro não existe!");
                }

                break;
        
            case "5":
                System.out.println("🔑 ALUGAR CARRO");
                System.out.println(" > Qual o ID? ");
                Integer id = sc.nextInt();

                try {
                    reservation.reserveCarro(id);

                    System.out.println("✅ Carro alugado com Sucesso!");
                } catch (CarroNotExistsException err){
                    System.out.println("🚫 O carro não existe!");
                } catch (InvalidOccupancyStatusException err){
                    System.out.println("⛔ O carro já está alugado!");
                }

                break;

            case "6":
                System.out.println("🔄 CANCELAR LOCAÇÃO");
                System.out.println(" > Qual o ID? ");
                Integer IDCarro = sc.nextInt();

                try {
                    reservation.cancelReservation(IDCarro);
                    System.out.println("✅ Locação cancelada com sucesso!");
                } catch (CarroNotExistsException err){
                    System.out.println("🚫 O carro não existe!");
                } catch (InvalidOccupancyStatusException err){
                    System.out.println("⛔ O carro não está alugado!");
                }
                break;

            case "7":
                System.out.println("\n\033[1;33m🛑 Encerrando o sistema... Obrigado por usar nosso serviço!\033[0m");
                inLooping = false;
                break;

            default:
                break;
        }
    }
    
    public static void main(String[] args){

        String operation;
        while(inLooping){
            printMenu();
            operation = sc.next();
            interpretInput(operation);
        }

        sc.close();
    }
}
