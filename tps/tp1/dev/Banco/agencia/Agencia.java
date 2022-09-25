package agencia;

import java.util.InputMismatchException;
import java.util.Scanner;
import javax.management.InvalidAttributeValueException;
import conta.Conta;
import conta.SortConta;

/*
 * Classe para fazer interface com o usuário e realizar ações de IO do banco
 */

public class Agencia {

    public static Scanner scan = new Scanner(System.in);
    public static boolean running = false;
    public static Operacoes operacao;

    public static void main(String[] args) {
        int op = 0;
        try {
            System.out.println("=============================================================");
            System.out.println("=                       BANCO MÁGICO                        =");
            System.out.println("=                      THE MAGIC BANK                       =");
            System.out.println("=============================================================");
            Thread.sleep(2000);
            operacao = new Operacoes();
            running = true;
            while (running) {
                try {
                    System.out.println("\nBem Vindo ao banco mágico, escolha uma opção:");
                    System.out.println("1- Criar conta no banco magic:");
                    System.out.println("2- Transferência entre contas:");
                    System.out.println("3- Verificar conta e ler dados:");
                    System.out.println("4- Atualizar dados de conta:");
                    System.out.println("5- Deletar conta do banco:");
                    System.out.println("6- Ordenar arquivo de contas:");
                    System.out.println("0- Sair do sistema e finalizar operações");
                    System.out.println("Digite a opção de operação:");
                    op = scan.nextInt();
                    if (op != 0)
                        menu(op);
                    else
                        running = false;
                    System.out.println("\n---------------------------------------------------------------");
                } catch (Exception e) {
                    System.err.println("Erro no loop da interface com usuário");
                    e.printStackTrace();
                }
            }
            scan.close();
            System.out.println("");
            System.out.println("================================================================");
            System.out.println("=                       OBRIGADO POR USAR                      =");
            System.out.println("=                         THE MAGIC BANK                       =");
            System.out.println("================================================================");
        } catch (Exception e) {
            System.err.println("Erro no sistema principal da agência do banco\n" +
                    "Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void menu(int op) {
        boolean status = false;
        try {
            switch (op) {
                case 1:
                    status = operacao.criarConta(menuCreate());
                    if (status) {
                        System.out.println("Inserido com sucesso na base de dados!");
                    }
                    break;
                case 2:
                    System.out.println("Transferência mágica:");
                    System.out.println("Digite o ID da conta que vai debitar o valor:");
                    int idb = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Digite o ID da conta que vai receber o valor:");
                    int idr = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Digite o valor que vai ser transferido:");
                    float val = scan.nextFloat();
                    try {
                        status = operacao.transferencia(operacao.ler(idb),
                                operacao.ler(idr), val);
                    } catch (InternalError ie) {
                        System.err.println("Erro interno: " + ie.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Digite o ID númerico da conta desejada:");
                    int id = scan.nextInt();
                    Conta ct = operacao.ler(id);
                    if (ct != null) {
                        System.out.println("Conta:\n" + ct.toString());
                        status = true;
                    } else {
                        status = true;
                        System.out.println("Nenhuma conta localizada com esse ID");
                    }
                    break;
                case 4:
                    status = operacao.atualizarConta(menuUpdate());
                    if (status) {
                        System.out.println("Atualizado com sucesso na base de dados!");
                    }
                    break;
                case 5:
                    System.out.println("Exclusão de usuário do banco mágico");
                    System.out.println("Digite o id do usuário:");
                    int idc = scan.nextInt();
                    status = operacao.deletarConta(idc);
                    if (status) {
                        System.out.println("Deletado com sucesso no sistema!");
                    } else {
                        System.err.println("O usuário não foi deletado ou não foi localizado");
                    }
                    break;
                case 6:
                    System.out.println("Ordenar arquivo de contas:");
                    status = menuSort();
                    break;
                default:
                    throw new InputMismatchException(op + " não é válido para executar no sistema.");
            }
            if (!status) {
                System.err.println("Suas ações não foram completas internamente, verifique os dados inseridos\n" +
                        "e tente novamente logo após!");
            } else {
                System.out.println("As ações foram finalizados e ocorreu tudo certo!");
            }
        } catch (InputMismatchException im) {
            System.err.println("Opção inválida para o menu! " + im.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao tentar realizar ação " + op + "\nErro:" + e.getMessage());
        }
    }

    public static Conta menuCreate() {
        try {
            Conta ct;
            String nome, nomeUser, cpf, cidade, senha;
            float saldo;
            int transferencias;
            String[] emails;
            System.out.println("Criação de conta mágica:");
            System.out.println("Digite o nome completo do cliente:");
            nome = scan.nextLine();
            System.out.println("Digite o nome de usuário do cliente:");
            nomeUser = scan.nextLine();
            System.out.println("Digite o CPF do cliente:");
            cpf = scan.nextLine();
            System.out.println("Digite a cidade do cliente:");
            cidade = scan.nextLine();
            System.out.println("Digite a quantidade de emails do cliente: Ex: 2");
            int qtd = scan.nextInt();
            scan.nextLine();
            emails = new String[qtd];
            for (int i = 0; i < qtd; i++) {
                System.out.println("Digite o " + i + 1 + " email:");
                emails[i] = scan.nextLine();
            }
            System.out.println("Digite a senha da conta do usuário:");
            senha = scan.nextLine();
            System.out.println("Digitet o saldo da conta do cliente: ex: 120.00");
            saldo = scan.nextFloat();
            scan.nextLine();
            System.out.println("Digite o total de transferências já realizadas pelo cliente: Ex: 1");
            transferencias = scan.nextInt();
            if (nome == null || nome.length() < 5 || nome.equals(" ")) {
                throw new InvalidAttributeValueException(
                        "O nome informado não pode estar vazio ou conter menos de 5 caracteres");
            }
            if (nomeUser == null || nomeUser.length() < 5 || nomeUser.equals(" ")) {
                throw new InvalidAttributeValueException(
                        "O nome de usuario informado não pode estar vazio ou conter menos de 5 caracteres");
            }
            if (cpf == null || cpf.length() < 11 || cpf.equals(" ")) {
                throw new InvalidAttributeValueException(
                        "O cpf de usuario informado não pode estar vazio ou conter menos de 11 caracteres");
            }
            if (senha == null || senha.length() < 4 || senha.equals(" ")) {
                throw new InvalidAttributeValueException(
                        "A senha do usuario informado não pode estar vazia ou conter menos de 4 caracteres");
            }
            if (qtd == 0 || emails.length == 0) {
                throw new InvalidAttributeValueException(
                        "Pelo menos um email deve ser inserido no sistema para o cadastro");
            }
            for (String em : emails) {
                if (em == null || em.length() < 5 || !em.contains("@")) {
                    throw new InvalidAttributeValueException("O email informado se encontra errado ou com falta do @");
                }
            }
            ct = new Conta(transferencias, nome, nomeUser, senha, cpf, cidade, emails, saldo);
            return ct;
        } catch (InvalidAttributeValueException ie) {
            System.err.println("Falha no tratamento de dados:\n" + ie.getMessage());
            return null;
        } catch (InputMismatchException ie) {
            System.err.println("O valor informado para o campo é inválido, tente novamente!\n" + ie.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Erro inesperado ao criar conta");
            return null;
        }
    }

    public static Conta menuUpdate() {
        try {
            Conta ct;
            String nome, nomeUser, cpf, cidade, senha;
            float saldo;
            int transferencias;
            String[] emails;
            System.out.println("Alteração de conta mágica:");
            System.out.println("Digite o id da conta:");
            int id = scan.nextInt();
            scan.nextLine();
            ct = operacao.ler(id);
            if (ct == null)
                throw new InvalidAttributeValueException("ID do cliente não localizado internamente para alterar");
            System.out.println("Digite o nome completo do cliente:");
            nome = scan.nextLine();
            System.out.println("Digite o nome de usuário do cliente:");
            nomeUser = scan.nextLine();
            System.out.println("Digite o CPF do cliente:");
            cpf = scan.nextLine();
            System.out.println("Digite a cidade do cliente:");
            cidade = scan.nextLine();
            System.out.println("Digite a quantidade de emails do cliente: Ex: 2");
            int qtd = scan.nextInt();
            scan.nextLine();
            emails = new String[qtd];
            for (int i = 0; i < qtd; i++) {
                System.out.println("Digite o " + i + 1 + " email:");
                emails[i] = scan.nextLine();
            }
            System.out.println("Digite a senha da conta do usuário:");
            senha = scan.nextLine();
            System.out.println("Digitet o saldo da conta do cliente: ex: 120.00");
            saldo = scan.nextFloat();
            scan.nextLine();
            System.out.println("Digite o total de transferências já realizadas pelo cliente: Ex: 1");
            transferencias = scan.nextInt();
            if (nome == null || nome.length() < 5 || nome.equals(" ")) {
                throw new InvalidAttributeValueException(
                        "O nome informado não pode estar vazio ou conter menos de 5 caracteres");
            }
            if (nomeUser == null || nomeUser.length() < 5 || nomeUser.equals(" ")) {
                throw new InvalidAttributeValueException(
                        "O nome de usuario informado não pode estar vazio ou conter menos de 5 caracteres");
            }
            if (cpf == null || cpf.length() < 11 || cpf.equals(" ")) {
                throw new InvalidAttributeValueException(
                        "O cpf de usuario informado não pode estar vazio ou conter menos de 11 caracteres");
            }
            if (senha == null || senha.length() < 4 || senha.equals(" ")) {
                throw new InvalidAttributeValueException(
                        "A senha do usuario informado não pode estar vazia ou conter menos de 4 caracteres");
            }
            if (qtd == 0 || emails.length == 0) {
                throw new InvalidAttributeValueException(
                        "Pelo menos um email deve ser inserido no sistema para o cadastro");
            }
            for (String em : emails) {
                if (em == null || em.length() < 5 || !em.contains("@")) {
                    throw new InvalidAttributeValueException("O email informado se encontra errado ou com falta do @");
                }
            }
            ct.setTransferenciasRealizadas(transferencias);
            ct.setCidade(cidade);
            ct.setCpf(cpf);
            ct.setEmail(emails);
            ct.setNomePessoa(nome);
            ct.setNomeUsuario(nomeUser);
            ct.setSaldoConta(saldo);
            ct.setSenha(senha);
            return ct;
        } catch (InvalidAttributeValueException ie) {
            System.err.println("Falha no tratamento de dados:\n" + ie.getMessage());
            return null;
        } catch (InputMismatchException ie) {
            System.err.println("O valor informado para o campo é inválido, tente novamente!\n" + ie.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Erro inesperado ao atualizar conta");
            return null;
        }
    }

    public static boolean menuSort() {
        try {
            boolean status = false;
            System.out.println("1- Intercalação balanceada comum");
            System.out.println("2- Intercalação balanceada com blocos de segmento variável");
            System.out.println("3- Intercalação balanceada com seleção por substituição");
            System.out.println("4- Intercalação balanceada com n+1 arquivos");
            System.out.println("5- Intercalação Polifásica");
            int op = scan.nextInt();
            System.out.println("Digite o total de registros 'm':");
            int m = scan.nextInt();
            System.out.println("Digite o total de caminhos 'n':");
            int n = scan.nextInt();
            SortConta sorts = new SortConta();
            switch (op) {
                case 1:
                    sorts.intercalacaoBal(m, n);
                    break;
                case 2:
                    sorts.intercalacaoTamVar(m, n);
                    break;
                case 3:
                    sorts.intercalacaoSelec(m, n);
                    break;
                case 4:
                    sorts.intercalacaoBalNMO(m, n);
                    break;
                case 5:
                    sorts.intercalacaoPoli(m, n);
                    break;
                default:
                    throw new Exception("Opção inválida");
            }
            return status;
        } catch (Exception e) {
            System.err.println("Erro interno ao tentar realziar intercalação\nErro: " + e.getMessage());
            return false;
        }
    }

}
