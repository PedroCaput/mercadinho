package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import modelo.Produto;
import utils.Utils;

public class Mercado {
	private static Scanner input = new Scanner(System.in);
	private static ArrayList<Produto> produtos;
	private static Map<Produto, Integer> carrinho;
	
	public static void main(String[] args) {
		produtos = new ArrayList<>();
		carrinho = new HashMap<>(); //interface que implementa o MAP
		menu();
	}
	private static void menu() {
		imprimir("-----------------------------------------------");
		imprimir("----------> Bem vindo ao Mercadinho <----------");
		imprimir("-----------------------------------------------");
		imprimir("-- Selecione uma operação que deseja realizar--");
		imprimir("-----------------------------------------------");
		imprimir("|         Opção 1 - Cadastrar produto         |");
		imprimir("|         Opção 2 - Listar produtos           |");
		imprimir("|         Opção 3 - Comprar                   |");
		imprimir("|         Opção 4 - Mostrar carrinho          |");
		imprimir("|         Opção 5 - Sair                      |");
		imprimir("-----------------------------------------------");
	
		boolean programaLigado = true;
		do{
			try {
				int option = input.nextInt();
					switch(option) {
						case 1:
							cadastrarProdutos();
							break;
						
						case 2:
							listarProdutos();
							break;
							
						case 3:
							comprarProdutos();
							break;
							
						case 4:
							verCarrinho();
							break;
							
						case 5:
							imprimir("Agradecemos por usar nosso programa.");
							programaLigado = false;
							imprimir("programaLigado possui o valor: " + programaLigado);
							System.exit(1);
						case 10:
							option = option - option;
							break;
						
						default:
							imprimir("Por favor, digite uma opção válida");
							menu();
							break;
					}
			}catch (Exception InputMismatchException) {
                System.out.println("Você digitou algo irregular, vamos começar de novo.");
                break;
            }
		}while(programaLigado = true);
	}
	static void imprimir(String texto) {
		System.out.println(texto);
	}
	private static void cadastrarProdutos() {
		imprimir("Qual o nome do produto? ");
		String nome = input.next();
		
		imprimir("Qual o preço do produto? ");
		Double preco = input.nextDouble();
		
		Produto produto = new Produto(nome, preco);
		produtos.add(produto);
		imprimir("O produto " + produto.getNome() + " foi cadastrado com sucesso!");
		menu();
	}
	private static void listarProdutos() {
		if(produtos.size() > 0) {
			imprimir("Lista de produtos: \n");
			for(Produto p : produtos) {
				System.out.println(p);
			}	
		}else {
			imprimir("No momento não possuímos produtos cadastrados.");
		}
		menu();
	}
	private static void comprarProdutos() {
		if(produtos.size() > 0 ) {
			imprimir("------------ Produtos disponíveis: ------------");
			for(Produto p : produtos) {
				System.out.println(p + "\n");
			}
			imprimir("-----------------------------------------------");
			imprimir("Qual o código do produto que deseja comprar?");
			int id = Integer.parseInt(input.next());
			boolean isPresent = false;
			
			for(Produto p : produtos) {
				if(p.getId() == id) {
					int quantidade = 0;
					try {
						//checa se o produto já etá no carrinho, incrementa quantidade
						quantidade = carrinho.get(p);
						carrinho.put(p, quantidade + 1);
					}catch(NullPointerException e) {
						//se o produto não for o primeiro no carrinho
						carrinho.put(p, 1);	
					}
					System.out.println(p.getNome() + " adicionado ao carrinho.");
					isPresent = true;
					if(isPresent) {
						imprimir("Deseja adicionar outro produto ao carrinho?");
						imprimir("Digite 1 para sim, ou 0 para finalizar a compra: ");
						int option = Integer.parseInt(input.next());
						
						if(option == 1) {
							comprarProdutos();
						}else if(option == 0){
							finalizarCompra();
						}
					}else {
						imprimir("Produto não encontrado.");
						menu();
					}
				}
			}
		}else {
			imprimir("Estamos sem estoque disponível.");
			menu();
		}
	}
	private static void verCarrinho() {
		if(carrinho.size() > 0) {
			imprimir("----- Produtos adicionados ao seu carrinho ----");
			for(Produto p : carrinho.keySet()) {
				System.out.println("Produto: " + p);
				System.out.println("Quantidade: " + carrinho.get(p));
			}
		}else {
			imprimir("Você ainda não adicionou nenhum produto ao seu carrinho");
		}
		menu();
	}
	private static void finalizarCompra() {
		Double valorDaCompra = 0.0;
		imprimir("-----------------------------------------------");
		imprimir("---------------- Seus produtos ----------------");
		for (Produto p : carrinho.keySet()) {
			int quantidade = carrinho.get(p);
			valorDaCompra = p.getPreco() * quantidade;
			System.out.println(p);
			System.out.println("Quantidade: " + quantidade);
		}
		System.out.println("O valor da sua compra é: " + Utils.doubleToString(valorDaCompra));
		carrinho.clear();
		imprimir("Obrigado pela preferência.");
		menu();
	}
}
