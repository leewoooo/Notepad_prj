package notepad.copy;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JavaMemoFontEvt extends WindowAdapter implements ActionListener, ListSelectionListener {

	// Has - A 관계를 위한 instance 선언
	private JavaMemoFont jmf;
	private JavaMemo jm;

	// JList에서 선택될 때마다 preview서식을 바꾸기 위한 preview 서식변수 선언
	private String font;
	private int style;
	private int size;

	// Has-A 관계 맺기
	public JavaMemoFontEvt(JavaMemo jm, JavaMemoFont jmf) {
		this.jmf = jmf;
		this.jm = jm;

		// 새로 생성될 font의 기본값을 설정

		font = jmf.getPreviewFont();
		style = jmf.getPreviewStyle();
		size = jmf.getPreviewSize();

	}// JavaMemoFontEvt

	// ListSelectionEvent는 두번 실행되기 때문에 1번실행으로 제어하기 위한 변수 선언
	private boolean flag;

	@Override
	public void valueChanged(ListSelectionEvent le) {

		if (flag) {
			// JList가 눌릴때마다 preview의 값이 실시간 변경되기 위한 이벤트들

			if (le.getSource() == jmf.getJlFont()) {
				jmf.getJtfFont().setText(jmf.getDlmFont().get(jmf.getJlFont().getSelectedIndex()));
				this.font = JavaMemo.FONT[jmf.getJlFont().getSelectedIndex()];
				jmf.getJiblPreview().setFont(new Font(font, style, size));
			} // end if

			if (le.getSource() == jmf.getJlStyle()) {
				jmf.getJtfStyle().setText(jmf.getDlmStyle().get(jmf.getJlStyle().getSelectedIndex()));
				this.style = JavaMemo.STYLE[jmf.getJlStyle().getSelectedIndex()];
				jmf.getJiblPreview().setFont(new Font(font, style, size));
			} // end if

			if (le.getSource() == jmf.getJlSize()) {
				jmf.getJtfSize().setText(String.valueOf(jmf.getDlmSize().get(jmf.getJlSize().getSelectedIndex())));
				this.size = JavaMemo.SIZE[(int) (jmf.getJlSize().getSelectedIndex())];
				jmf.getJiblPreview().setFont(new Font(font, style, size));
			} // end if
		} // end if
		
		flag = !flag;
	}// valueChanged

	@Override
	public void actionPerformed(ActionEvent ae) {

		// ComboBox의 값이 변경될 때마다 preview text변경 이벤트
		if (ae.getSource() == jmf.getJcbScript()) {
			String flag = jmf.getDjcbScript().getElementAt(jmf.getJcbScript().getSelectedIndex());
			if ("한글".equals(flag)) {
				jmf.getJiblPreview().setText("AaBbCc가나다");
			} // end if
			if ("영어".equals(flag)) {
				jmf.getJiblPreview().setText("AaBbCc");
			} // end if
		} // end if

		// Font Dialog 닫기 이벤트
		if (ae.getSource() == jmf.getJbtnClose()) {
			jmf.dispose();
		} // end if

		// Font Dialog 적용 이벤트
		if (ae.getSource() == jmf.getJbtnApply()) {
			jm.getJtaMemo().setFont(new Font(font, style, size));
			jmf.dispose();
		} // end if

	}// actionPerformed

	@Override
	public void windowClosing(WindowEvent we) {
		jmf.dispose();
	}// windowClosing

}// JavaMemoFontEvt
