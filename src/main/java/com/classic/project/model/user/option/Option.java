package com.classic.project.model.user.option;

import com.classic.project.model.user.User;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table
public class Option {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int optionId;

    @Column
    @ColumnDefault(value = "false")
    private boolean bossesOption;

    @Column
    @ColumnDefault(value = "false")
    private boolean newItemOption;

    @Column
    @ColumnDefault(value = "false")
    private boolean soldItemOption;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User userOption;

    public int getOptionId() {
	return optionId;
    }

    public void setOptionId(int optionId) {
	this.optionId = optionId;
    }

    public boolean isBossesOption() {
	return bossesOption;
    }

    public void setBossesOption(boolean bossesOption) {
	this.bossesOption = bossesOption;
    }

    public boolean isNewItemOption() {
	return newItemOption;
    }

    public void setNewItemOption(boolean newItemOption) {
	this.newItemOption = newItemOption;
    }

    public boolean isSoldItemOption() {
	return soldItemOption;
    }

    public void setSoldItemOption(boolean soldItemOption) {
	this.soldItemOption = soldItemOption;
    }

    @JsonIgnore
    public User getUserOption() {
	return userOption;
    }

    public void setUserOption(User userOption) {
	this.userOption = userOption;
    }
}
