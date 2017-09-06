package vn.kms.rabbitholes.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author kietlam
 */
@Entity
@Table(name = "employee")
public class Employee {

    private int id;
    private String name;
    private double salary;
    private String deg;
}
