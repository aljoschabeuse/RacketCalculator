package application;

import javafx.scene.control.*;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class Customer {
	private String name;
	private boolean bar;
	private boolean member;
	private boolean hansefit;
	private boolean child;
	private boolean student;
	private boolean tennisWA;
	private boolean tennisJS;
	private boolean familie;
	private float amount;
	public Customer(String name, boolean member, boolean hansefit, boolean child, boolean student, boolean tennisWA, boolean tennisJS, boolean familie) {
		this.name = name;
		this.member = member;
		this.hansefit = hansefit;
		this.child = child;
		this.student = student;
		if (!member && !hansefit && !child && !student && !tennisWA && !tennisJS) {
			bar = true;
		} else {
			bar = false;
		}
		this.tennisWA = tennisWA;
		this.tennisJS = tennisJS;
		this.familie = familie;
	}
	
	public void updateCustomer(CheckBox bar, CheckBox member, CheckBox hansefit, CheckBox child, CheckBox student, CheckBox tennisWA, CheckBox tennisJS) {
		if (!this.member && !this.hansefit && !this.student && !this.tennisWA && !this.tennisJS) {
			this.bar = true;
		}
		if (this.member) {
			child.setDisable(false);
		} else {
			child.setDisable(true);
		}
		bar.setSelected(this.bar);
		member.setSelected(this.member);
		hansefit.setSelected(this.hansefit);
		child.setSelected(this.child);
		student.setSelected(this.student);
		tennisWA.setSelected(this.tennisWA);
		tennisJS.setSelected(this.tennisJS);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getBar() {
		return bar;
	}
	
	public boolean getMember() {
		return member;
	}
	
	public boolean getHansefit() {
		return hansefit;
	}
	
	public boolean getChild() {
		return child;
	}
	
	public boolean getStudent() {
		return student;
	}
	
	public boolean getTennisWA() {
		return tennisWA;
	}
	
	public boolean getTennisJS() {
		return tennisJS;
	}
	
	public boolean getFamilie() {
		return familie;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public boolean[] getStatus() {
		return new boolean[] {bar, member, hansefit, child, student};
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBar(boolean bar) {
		this.bar = bar;
	}
	
	public void setMember(boolean member) {
		this.member = member;
	}
	
	public void setHansefit(boolean hansefit) {
		this.hansefit = hansefit;
	}
	
	public void setChild(boolean child) {
		this.child = child;
	}
	
	public void setStudent(boolean student) {
		this.student = student;
	}
	
	public void setTennisWA(boolean tennisWA) {
		this.tennisWA = tennisWA;
	}
	
	public void setTennisJS(boolean tennisJS) {
		this.tennisJS = tennisJS;
	}
	
	public void setFamilie(boolean familie) {
		this.familie = familie;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public void printCustomer() {
		System.out.println(this.name+"\n"+
				"Bar: "+this.bar+"\n"+
				"Mitglied: "+this.member+"\n"+
				"Hansefit: "+this.hansefit+"\n"+
				"Kind v. Mitglied: "+this.child+"\n"+
				"Student: "+this.student+"\n"+
				"Tennis-Abo WA: "+this.tennisWA+"\n"+
				"Tennis-Abo JS: "+this.tennisJS+"\n");
	}
}
