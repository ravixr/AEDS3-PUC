package conta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Conta {

    /*
     * Descrição dos campos de Conta:
     * 
     * idConta (deve ser incremental à medida que novos registros forem adicionados)
     * (int)
     * nomePessoa (string de tamanho variável)
     * email (1 ou mais) (strings de tamanhos variáveis com indicador de quantidade)
     * nomeUsuario (string de tamanho variável)
     * senha (string de tamanho variável)
     * cpf (string de tamanho fixo igual a 11)
     * cidade (string de tamanho variável)
     * transferenciasRealizadas (int)
     * saldoConta (float)
     */

    private int idConta, transferenciasRealizadas;
    private String nomePessoa, nomeUsuario, senha, cpf, cidade, email[];
    private float saldoConta;

    public Conta() {
        this.idConta = -1;
        this.transferenciasRealizadas = 0;
        this.nomePessoa = "";
        this.nomeUsuario = "";
        this.senha = "";
        this.cpf = "";
        this.cidade = "";
        this.email = null;
        this.saldoConta = 0f;
    }

    public Conta(int idConta, int transferenciasRealizadas, String nomePessoa, String nomeUsuario, String senha,
            String cpf, String cidade, String[] email, float saldoConta) {
        this.idConta = idConta;
        this.transferenciasRealizadas = transferenciasRealizadas;
        this.nomePessoa = nomePessoa;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.cpf = cpf;
        this.cidade = cidade;
        this.email = email;
        this.saldoConta = saldoConta;
    }

    public byte[] toByteArray() throws IOException {
        byte[] data;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(getIdConta());
        dos.writeUTF(getNomePessoa());
        dos.writeInt(getEmail().length);
        for (String email : getEmail()) {
            dos.writeUTF(email);
        }
        dos.writeUTF(getNomeUsuario());
        dos.writeUTF(getSenha());
        dos.writeUTF(getCpf());
        dos.writeUTF(getCidade());
        dos.writeInt(getTransferenciasRealizadas());
        dos.writeFloat(getSaldoConta());
        data = baos.toByteArray();
        baos.close();
        dos.close();
        return data;
    }

    public void fromByteArray(byte ba[]) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        setIdConta(dis.readInt());
        setNomePessoa(dis.readUTF());
        this.email = new String[dis.readInt()];
        for (int i = 0; i < this.email.length; i++) {
            this.email[i] = dis.readUTF();
        }
        setNomeUsuario(dis.readUTF());
        setSenha(dis.readUTF());
        setCpf(dis.readUTF());
        setCidade(dis.readUTF());
        setTransferenciasRealizadas(dis.readInt());
        setSaldoConta(dis.readFloat());
        bais.close();
        dis.close();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Conta other = (Conta) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (idConta != other.idConta)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Conta [cidade=" + cidade + ", cpf=" + cpf + ", email=" + Arrays.toString(email) + ", idConta=" + idConta
                + ", nomePessoa=" + nomePessoa + ", nomeUsuario=" + nomeUsuario + ", saldoConta=" + saldoConta
                + ", senha=" + senha + ", transferenciasRealizadas=" + transferenciasRealizadas + "]";
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public int getTransferenciasRealizadas() {
        return transferenciasRealizadas;
    }

    public void setTransferenciasRealizadas(int transferenciasRealizadas) {
        this.transferenciasRealizadas = transferenciasRealizadas;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public float getSaldoConta() {
        return saldoConta;
    }

    public void setSaldoConta(float saldoConta) {
        this.saldoConta = saldoConta;
    }

}