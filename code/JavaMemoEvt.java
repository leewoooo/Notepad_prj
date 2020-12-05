package notepad;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JOptionPane;

public class JavaMemoEvt extends WindowAdapter implements ActionListener {

	private JavaMemo jm;

	// 열기버튼이 선택되어 파일이 열렸는지 확인하기 위한 Flag변수
	private boolean openFlag;

	// 현재 열려있는 텍스트 파일
	private File file;
	// 현재 열려있는 텍스트 파일의 초기 메모장 내용
	private String memo;

	public JavaMemoEvt(JavaMemo jm) {
		this.jm = jm;
	}// JavaMemoEvt

	@Override
	public void actionPerformed(ActionEvent ae) {

		// 새글

		// 새 글 메뉴가 선택되면 메모장 영역 초기화
		if (ae.getSource() == jm.getJmiNew()) {
			// 파일이 열린적이 없고 메모장의 내용이 있을때 새글이 눌린 경우
			if (!openFlag && !jm.getJtaMemo().getText().isEmpty()) {
				int option = askNewFileSave();
				switch (option) {
				case JOptionPane.OK_OPTION:
					// 새이름으로 저장 수행
					newSave();
					// 메모장 초기화 및 tItle 수정
					resetTitle_resetText();
					break;
				case JOptionPane.NO_OPTION:
					// 메모장의 내용을 초기화
					resetTitle_resetText();
					break;
				}// end switch
					// 파일이 열린적이 있을 때 새글이 눌린 경우
			} else if (openFlag) {
				// 파일을 열었을때의 초기상태와 동일한지?
				if (memo.equals(jm.getJtaMemo().getText())) { // 파일이 열렸을 때와 동일한지 비교
					// 동일할 시 메모장 초기화
					resetTitle_resetText();
					openFlag = !openFlag;
					// 파일을 열었을때의 초기상태와 동일하지 않을경우.
				} else {
					int option = askOverwrite();
					switch (option) {
					case JOptionPane.OK_OPTION:
						save(this.file);
						resetTitle_resetText();
						//저장후 초기상태로 돌리기위한 openFlag 뒤집기
						openFlag = !openFlag;
						break;
					case JOptionPane.NO_OPTION:
						// 메모장의 내용을 초기화
						resetTitle_resetText();
						//저장후 초기상태로 돌리기위한 openFlag 뒤집기
						openFlag = !openFlag;
					}// end switch
				} // end else
			} // end if
		} // end if

		//

		// 열기

		// 열기를 누르면 File Load창 열기
		if (ae.getSource() == jm.getJmiOpen()) {

			// 파일이 열린적이 없을시
			if (!openFlag && jm.getJtaMemo().getText().isEmpty()) {
				openFile();
				// 파일이 열린적이 없고 메모장의 내용이 있는 상태에서 열기가 눌린 경우
			} else if (!openFlag && !jm.getJtaMemo().getText().isEmpty()) {
				int option = askNewFileSave();
				switch (option) {
				case JOptionPane.OK_OPTION:
					// 새 이름으로 저장
					newSave();
					// 저장 후 초기화
					resetText();
					// 파일 열기
					openFile();
					break;
				case JOptionPane.NO_OPTION:
					// 초기화
					resetText();
					// 파일 열기
					openFile();
				}// end switch
					// 열기버튼을 사용하여 파일을 연적이 있는 경우
			} else if (openFlag) {
				// 파일을 열었을때의 초기상태와 동일한지?
				if (memo.equals(jm.getJtaMemo().getText())) { // 파일을 열었을 때의 초기상태와 비교
					// 동일할 시 열기 기능 제공
					openFile();
					// 파일을 열었을때의 초기상태와 동일하지 않을경우.
				} else if(!memo.equals(jm.getJtaMemo().getText())) {
					int option = askOverwrite();
					switch (option) {
					case JOptionPane.OK_OPTION:
						save(this.file);
						resetText();
						openFile();
						break;
					case JOptionPane.NO_OPTION:
						resetText();
						openFile();
						break;
					}// end switch
				} else if (jm.getJtaMemo().getText().isEmpty()) {
					openFile();
				}
			} // end if
		} // end if

		//

		// 저장

		// 저장을 누르면 File Save창 열기
		if (ae.getSource() == jm.getJmiSave()) {
			// 파일이 열린적이 없으며 저장이 눌린 경우
			if (!openFlag && (((jm.getJtaMemo().getText().isEmpty())) || (!jm.getJtaMemo().getText().isEmpty()))) {
				newSave();
			} else if (openFlag) {
				save(this.file);
				//저장 후 현재 상태에 메모장내용을 저장
				memo = jm.getJtaMemo().getText();
			} // end if
		} // end if

		//

		// 새이름으로 저장

		// 새이름으로 누르면 File Save창 열기
		if (ae.getSource() == jm.getJmiNewSave()) {
			newSave();
		} // end if
		
		//

		//닫기
		
		// 닫기를 누르면 메모장 종료
		if (ae.getSource() == jm.getJmiClose()) {
			//파일이 열린적이 없으며 내용이 있는 상태에서 닫기가 눌린경우
			if (!openFlag && !jm.getJtaMemo().getText().isEmpty()) {
				int option = askNewFileSave();
				switch (option) {
				case JOptionPane.OK_OPTION:
					newSave();
					jm.dispose();
					break;
				case JOptionPane.NO_OPTION:
					jm.dispose();
				}// end switch
			} else if (openFlag) {
				if (memo.equals(jm.getJtaMemo().getText())) {
					jm.dispose();
				} else if (!memo.equals(jm.getJtaMemo().getText())) {
					int option = askOverwrite();
					switch (option) {
					case JOptionPane.OK_OPTION:
						save(this.file);
						jm.dispose();
					case JOptionPane.NO_OPTION:
						jm.dispose();
					}// end switch
				} // end else
			} else {
				jm.dispose();
			}
		} // end if

		//
		
		// 글꼴을 누르면 MemoFont Dialog열기
		if (ae.getSource() == jm.getJmiFont()) {
			openFont(jm);
		} // end if

		// 메모장정보를 누르면 MemoHelp Dialog열기
		if (ae.getSource() == jm.getJmiHelp()) {
			openHelp(jm);
		} // end if

	}// actionPerformed

	// 글꼴 dialog생성
	public void openFont(JavaMemo jm) {
		new JavaMemoFont(jm);
	}// openFont

	// 도움말 dialog생성
	public void openHelp(JavaMemo jm) {
		new MemoHelp(jm);
	}// openFont

	@Override
	public void windowClosing(WindowEvent we) {
		jm.dispose();
	}// windowClosing

	/**
	 * 메모장을 reset하는 method Title 및 메모장 내용 reset
	 */
	public void resetTitle_resetText() {
		resetText();
		jm.setTitle("제목없음.txt");
	}

	/**
	 * 메모장을 reset하는 method 메모장의 내용
	 */
	public void resetText() {
		jm.getJtaMemo().setText("");
	}// resetText

	public void openFile() {
		FileDialog fd = new FileDialog(jm, "열기", FileDialog.LOAD);
		fd.setVisible(true);
		// 파일이 선택 되었을 시에만 사용된다.
		if (fd.getFile() != null) {
			try {
				file = new File(fd.getDirectory() + fd.getFile());
				// 스트림연결
				BufferedReader bfr = new BufferedReader(new java.io.InputStreamReader(new FileInputStream(file)));

				String msg = "";
				// txt파일에 글을 줄단위로 읽어와 메모장에 기록
				while ((msg = bfr.readLine()) != null) {
					jm.getJtaMemo().append(msg + "\n");
				} // end while
					// 스트림끊기
				bfr.close();

				// 열기 버튼 사용후 초기 메모장 내용
				memo = jm.getJtaMemo().getText();

				jm.setTitle("자바-메모장[" + fd.getFile() + "]");

				// openflag값 변경( 열기 기능 사용했음)

				// 열기가 눌린후 또 열기가 눌렸을 경우 첫 열기 버튼이 눌렸을 때만 flag 변경
				if (openFlag == false) {
					openFlag = !openFlag;
				}

			} catch (IOException ie) { // file이 선택되었을 시에만 try수행함으로 FileNotFoundException 생략
				ie.printStackTrace();
			} // end catch
		} // end if
	}// openFile

	public void newSave() {
		FileDialog fd = new FileDialog(jm, "저장", FileDialog.SAVE);
		fd.setVisible(true);
		if (fd.getFile() != null) {
//			 새이름으로 저장할 파일 생성
			File newfile = new File(fd.getDirectory() + fd.getFile());
			this.file = newfile;
			save(newfile);
			jm.setTitle("자바-메모장[저장 : " + fd.getFile() + "]");
		} // newSave
	}// newSave
	
	public void save(File file) {
		try {
			// 스트림연결
			BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			
			// 스트림에 기록하기
			//JtextArea는 줄바꿈을 "\n"을 하지만 windows에서 제공하는 메모장은 "\r\n"으로 하기에 바꿔준다.
			String msg = jm.getJtaMemo().getText().replaceAll("\n", "\r\n");
			
			bfw.write(msg);
			
			// 스트림에서 파일로
			bfw.flush();
			
			// 스트림 종료
			bfw.close();
			
		} catch (IOException ie) {
			ie.printStackTrace();
		} // end catch
	} // save

	
	public int askNewFileSave() {
		int option = JOptionPane.showConfirmDialog(jm, "현재 메모장의 내용을 새 파일에 저장하시겠습니까?");
		return option;
	}// askNewFileSave
	

	
	public int askOverwrite() {
		int option = JOptionPane.showConfirmDialog(jm,
				"현재 메모장의 내용을 " + this.file.getAbsolutePath()+ " 위치에 저장하시겠습니까?");
		return option;
	}//

}// JavaMemoEvt
