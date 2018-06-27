import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
//import java.awt.Font;
import java.awt.Font;
//import java.util.Properties;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


public class Signin extends JFrame{
	JLabel title, label1, label2, label3, label4, label5, label6, label7, label8;
	JTextField id, name, nickname, student_id, birthdate;
	JPasswordField password, checkPassword;
	Choice depart;
	JButton save, exit, checkID, date;
	UtilDateModel model;
	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker;
	
	
	Signin(){
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model, p);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 800);
		this.setLayout(null);
		this.setTitle("회원가입");
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.getHSBColor(150, 194, 255));
		
		label1 = new JLabel("ID ");
		label2 = new JLabel("Password ");
		label3 = new JLabel("Password Check ");
		label4 = new JLabel("Name ");
		label5 = new JLabel("NickName ");
		label6 = new JLabel("Birthday ");
		label7 = new JLabel("Student ID ");
		label8 = new JLabel("Student Department ");
		
		id = new JTextField();
		name = new JTextField();
		nickname = new JTextField();
		student_id = new JTextField();
		password = new JPasswordField();
		checkPassword = new JPasswordField();
		depart = new Choice();
		save = new JButton("가입하기");
		exit = new JButton("그만두기");
		checkID = new JButton("ID Check");
		datePicker = new JDatePickerImpl(datePanel, new BirthDate());
		birthdate = new JTextField();
		
		depart.add("경영경제학부");
		depart.add("공간환경시스템공학부");
		depart.add("국제어문학부");
		depart.add("글로벌리더십학부");
		depart.add("기계제어공학부");
		depart.add("법학부");
		depart.add("상담심리사회복지학부");
		depart.add("생명과학부");
		depart.add("언론정보문화학부");
		depart.add("전산전자공학부");
		depart.add("콘텐츠융합디자인학부");
		depart.add("ICT창업학부");
		
		label1.setBounds(100, 50, 120, 50);
		label2.setBounds(100, 130, 120, 50);
		label3.setBounds(100, 210, 120, 50);
		label4.setBounds(100, 290, 120, 50);
		label5.setBounds(100, 370, 120, 50);
		label6.setBounds(100, 450, 120, 50);
		label7.setBounds(100, 530, 120, 50);
		label8.setBounds(65, 590, 140, 50);
		
		id.setBounds(230, 50, 300, 50);
		checkID.setBounds(570, 50, 100, 50);
		password.setBounds(230, 130, 300, 50);
		checkPassword.setBounds(230, 210, 300, 50);
		name.setBounds(230, 290, 300, 50);
		nickname.setBounds(230, 370, 300, 50);
		birthdate.setBounds(230, 450, 300, 50);
		student_id.setBounds(230, 530, 300, 50);
		depart.setBounds(230, 590, 300, 50);
		save.setBounds(175, 700, 150, 30);
		exit.setBounds(375, 700, 150, 30);
		datePicker.setBounds(230, 450, 300, 50);
		
		label1.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		label2.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		label3.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		label4.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		label5.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		label6.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		label7.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		label8.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		
		
		id.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		checkID.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		password.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		checkPassword.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		name.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		nickname.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		datePicker.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		student_id.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		depart.setFont(new Font("나눔고딕", Font.PLAIN, 13));
		save.setFont(new Font("나눔고딕", Font.PLAIN,15));
		exit.setFont(new Font("나눔고딕", Font.PLAIN, 15));
				
		this.add(label1);
		this.add(id);
		this.add(checkID);
		this.add(label2);
		this.add(password);
		this.add(label3);
		this.add(checkPassword);
		this.add(label4);
		this.add(name);
		this.add(label5);
		this.add(nickname);
		this.add(label6);
		this.add(datePicker);
		this.add(label7);
		this.add(student_id);
		this.add(label8);
		this.add(depart);
		this.add(save);
		this.add(exit);	
		
		model.setSelected(true);
	}
}
