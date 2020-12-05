package notepad;


import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class JavaMemoFont extends JDialog{
	
	
	//이벤트에 관련된 component들 선언
	private JTextField jtfFont;
	private JTextField jtfStyle;
	private JTextField jtfSize;
	
	private JLabel jiblPreview;
	
	private DefaultComboBoxModel<String> djcbScript;
	private JComboBox<String> jcbScript;
	
	private DefaultListModel<String> dlmFont;
	private DefaultListModel<String> dlmStyle;
	private DefaultListModel<Integer> dlmSize;
	
	private JList<String> jlFont;
	private JList<String> jlStyle;
	private JList<Integer> jlSize;
	
	private JButton jbtnApply;
	private JButton jbtnClose;
	
	private String previewFont;
	private int previewStyle;
	private int previewSize;
	
	public JavaMemoFont(JavaMemo jm) {

		//생성자 작성.
		super(jm, "글꼴", false);

		//component생성
		
		
		JLabel jlblFont = new JLabel("글꼴");
		JLabel jlblStyle = new JLabel("스타일");
		JLabel jlblSize = new JLabel("크기");
		
		//초기 메모장의 서식값을 받아주기 위해 생성할 때 argument를 넣어 생성
		jtfFont = new JTextField(jm.getFt().getFontName());
		jtfStyle = new JTextField(JavaMemo.STYLENAME[jm.getFt().getStyle()]);
		jtfSize = new JTextField(String.valueOf(jm.getFt().getSize()));
		
		//미리보기창 기본 서식은 메모장의 기본서식과 동일
		previewFont = JavaMemo.FONT[0];
		previewStyle = JavaMemo.STYLE[0];
		previewSize = JavaMemo.SIZE[0];
		
		jiblPreview = new JLabel("AaBbCc");
		Font ftPreview = new Font(previewFont, previewStyle, previewSize);
		jiblPreview.setFont(ftPreview);
		TitledBorder tbPriview = new TitledBorder("스크립트");
		jiblPreview.setBorder(tbPriview);
		
		djcbScript = new DefaultComboBoxModel<String>();
		djcbScript.addElement("영어");
		djcbScript.addElement("한글");
		
		jcbScript = new JComboBox<String>(djcbScript);
		
		dlmFont = new DefaultListModel<String>();
		for(int i = 0; i < JavaMemo.FONT.length; i++) {
			dlmFont.addElement(JavaMemo.FONT[i]);
		}//end for
		jlFont = new JList<String>(dlmFont);
		
		dlmStyle = new DefaultListModel<String>();
		for(int i = 0; i < JavaMemo.STYLENAME.length; i++) {
			dlmStyle.addElement(JavaMemo.STYLENAME[i]);
		}//end for
		jlStyle = new JList<String>(dlmStyle);
		
		dlmSize = new DefaultListModel<Integer>();
		for(int i = 0; i < JavaMemo.SIZE.length; i++) {
			dlmSize.addElement(JavaMemo.SIZE[i]);
		}//end for
		jlSize = new JList<Integer>(dlmSize);
		JScrollPane jspSize = new JScrollPane(jlSize);
		
		jbtnApply = new JButton("적용");
		jbtnClose = new JButton("닫기");
		
		
		//이벤트 등록하기
		JavaMemoFontEvt jmfe = new JavaMemoFontEvt(jm,this);
		jlFont.addListSelectionListener(jmfe);
		jlStyle.addListSelectionListener(jmfe);
		jlSize.addListSelectionListener(jmfe);

		jcbScript.addActionListener(jmfe);
		
		jbtnApply.addActionListener(jmfe);
		jbtnClose.addActionListener(jmfe);
		
		
		//배치관리자
		setLayout(null);
		setResizable(false);
		
		//component 배치
		
		jlblFont.setBounds(50, 20, 80, 30);
		jtfFont.setBounds(50, 50, 80, 30);
		jlFont.setBounds(50, 100, 80, 150);
		
		add(jlblFont);
		add(jtfFont);
		add(jlFont);
		
		jlblStyle.setBounds(180, 20, 80, 30);
		jtfStyle.setBounds(180, 50, 80, 30);
		jlStyle.setBounds(180, 100, 80, 150);
		
		add(jlblStyle);
		add(jtfStyle);
		add(jlStyle);
		
		jlblSize.setBounds(310, 20, 80, 30);
		jtfSize.setBounds(310, 50, 80, 30);
		jspSize.setBounds(310, 100, 80, 150);
		
		add(jlblSize);
		add(jtfSize);
		add(jspSize);
		
		jiblPreview.setBounds(280, 270, 100, 50);
		add(jiblPreview);
		
		jcbScript.setBounds(310, 330, 80, 30);
		add(jcbScript);
		
		jbtnApply.setBounds(220, 370, 80, 30);
		add(jbtnApply);
		
		jbtnClose.setBounds(310, 370, 80, 30);
		add(jbtnClose);
		
		
		//window사이즈
		setBounds(jm.getX()+50, jm.getY(), 460, 470);
		
		//사용자에게 보여주기
		setVisible(true);
		
	 }//JavaMemoFont


	public JTextField getJtfFont() {
		return jtfFont;
	}


	public JTextField getJtfStyle() {
		return jtfStyle;
	}


	public JTextField getJtfSize() {
		return jtfSize;
	}


	public JLabel getJiblPreview() {
		return jiblPreview;
	}


	public DefaultComboBoxModel<String> getDjcbScript() {
		return djcbScript;
	}


	public JComboBox<String> getJcbScript() {
		return jcbScript;
	}


	public DefaultListModel<String> getDlmFont() {
		return dlmFont;
	}


	public DefaultListModel<String> getDlmStyle() {
		return dlmStyle;
	}


	public DefaultListModel<Integer> getDlmSize() {
		return dlmSize;
	}


	public JList<String> getJlFont() {
		return jlFont;
	}


	public JList<String> getJlStyle() {
		return jlStyle;
	}


	public JList<Integer> getJlSize() {
		return jlSize;
	}


	public JButton getJbtnApply() {
		return jbtnApply;
	}


	public JButton getJbtnClose() {
		return jbtnClose;
	}


	public String getPreviewFont() {
		return previewFont;
	}


	public int getPreviewStyle() {
		return previewStyle;
	}


	public int getPreviewSize() {
		return previewSize;
	}
	
	
	

}//JavaMemoFont
