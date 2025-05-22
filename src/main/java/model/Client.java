/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author enzoc
 */
public class Client {
    private int    id;      // chave primária no banco
    private String name;    // nome completo
    private String cpf;     // CPF formatado
    private String phone;   // telefone com máscara
    private String email;   // e-mail do cliente

    public Client() { }

    public Client(int id, String name, String cpf, String phone, String email) {
        this.id    = id;
        this.name  = name;
        this.cpf   = cpf;
        this.phone = phone;
        this.email = email;
    }
    
     // Getters e setters
    public int    getId()    { return id;    }
    public String getName()  { return name;  }
    public String getCpf()   { return cpf;   }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public void setId(int id)          { this.id = id;       }
    public void setName(String name)   { this.name = name;   }
    public void setCpf(String cpf)     { this.cpf = cpf;     }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
}
