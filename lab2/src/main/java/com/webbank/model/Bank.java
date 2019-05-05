package com.webbank.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BANKS")
public class Bank {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "NAME")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "BANK_ACC",
            joinColumns = { @JoinColumn(name = "BANK_ID", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "ACC_ID", referencedColumnName = "id") })
    private List<Account> accounts = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "BANK_ADMINS",
            joinColumns = { @JoinColumn(name = "BANK_ID", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "id") })
    private List<User> admins = new ArrayList<>();
    public void setId(int id){this.id = id;}
    public int getId(){return id;}
    public void setName(String name){this.name = name;}
    public String getName(){return name;}
    public void setAccounts(Account accounts){this.accounts.add(accounts);}
    public List<Account> getAccounts(){return accounts;}
    public void setAdmins(User admins){this.admins.add(admins);}
    public List<User> getAdmins(){return admins;}


}
