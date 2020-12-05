package notepad;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")

//Window component 상속
public class JavaMemo extends JFrame {

	// 메모장의 기본 서식을 constant로 지정하여 가져다 쓰기 위해 선언
	public static final String[] FONT = { "Dialog", "DialogInput", "Monospaced", "Serif", "SansSerif" };
	public static final String[] STYLENAME = { "PLAIN", "BOLD", "ITALIC", "BOLDITALIC" };
	public static final int[] STYLE = { Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC };
	// 글시 Sizesms 8~100까지 설정하기 위한 배열
	public static final int[] SIZE = new int[100 - 12 + 1];

	// 이벤트에 관련된 component선언

	private JTextArea jtaMemo;

	private JMenuItem jmiNew;
	private JMenuItem jmiOpen;
	private JMenuItem jmiSave;
	private JMenuItem jmiNewSave;
	private JMenuItem jmiClose;
	private JMenuItem jmiFont;
	private JMenuItem jmiHelp;

	private Font ft;

	// 생성자 작성
	public JavaMemo() {

		super("제목없음.txt");

		// SIZE에 값입력
		addSize();
//		//SIZE 입력값 확인
//		for(int i = 0; i < SIZE.length; i++) {
//		System.out.println(SIZE[i]);
//		}

		// component 생성

		JMenuBar jb = new JMenuBar();

		// text area
		jtaMemo = new JTextArea();
		ft = new Font(FONT[0], STYLE[0], SIZE[0]);
		jtaMemo.setFont(ft);
		JScrollPane jsp = new JScrollPane(jtaMemo);

		// file
		JMenu jmFile = new JMenu("File");
		jmiNew = new JMenuItem("새 글");
		jmiOpen = new JMenuItem("열기");
		jmiSave = new JMenuItem("저장");
		jmiNewSave = new JMenuItem("새 이름으로 저장");
		jmiClose = new JMenuItem("닫기");

		jmFile.add(jmiNew);
		jmFile.addSeparator();
		jmFile.add(jmiOpen);
		jmFile.add(jmiSave);
		jmFile.add(jmiNewSave);
		jmFile.addSeparator();
		jmFile.add(jmiClose);

		// font
		JMenu jmFont = new JMenu("Font");
		jmiFont = new JMenuItem("글꼴");
		jmFont.add(jmiFont);

		// help
		JMenu jmHelp = new JMenu("Help");
		jmiHelp = new JMenuItem("메모장정보");
		jmHelp.add(jmiHelp);

		// 이벤트 추가하기
		JavaMemoEvt jme = new JavaMemoEvt(this);
		jmiNew.addActionListener(jme);
		jmiOpen.addActionListener(jme);
		jmiSave.addActionListener(jme);
		jmiNewSave.addActionListener(jme);
		jmiClose.addActionListener(jme);

		jmiFont.addActionListener(jme);

		jmiHelp.addActionListener(jme);

		addWindowListener(jme);

		// menubar에 붙이기
		jb.add(jmFile);
		jb.add(jmFont);
		jb.add(jmHelp);

		// 배치관리자
		setLayout(new BorderLayout());

		// component 배치.
		setJMenuBar(jb);
		add(jsp);

		// window크기설정
		setBounds(50, 50, 500, 250);

		// 사용자에게 보여주기
		setVisible(true);

	}// JavaMemo

	// getter추가

	public JTextArea getJtaMemo() {
		return jtaMemo;
	}// getJtaMemo

	public JMenuItem getJmiNew() {
		return jmiNew;
	}// getJmiNew

	public JMenuItem getJmiOpen() {
		return jmiOpen;
	}// getJmiOpen

	public JMenuItem getJmiSave() {
		return jmiSave;
	}// getJmiSave

	public JMenuItem getJmiNewSave() {
		return jmiNewSave;
	}// getJmiNewSave

	public JMenuItem getJmiClose() {
		return jmiClose;
	}// getJmiClose

	public JMenuItem getJmiFont() {
		return jmiFont;
	}// getJmiFont

	public JMenuItem getJmiHelp() {
		return jmiHelp;
	}// getJmiHelp

	public Font getFt() {
		return ft;
	}// getFt

	/**
	 * Size 배열에 12부터 100까지 추가
	 * 
	 */
	public void addSize() {
		for (int i = 0; i < 89; i++) {
			SIZE[i] = 12 + i;
		} // end for
	}// addSize

}// class
