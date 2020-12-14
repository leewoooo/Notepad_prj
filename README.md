NOTEPAD
===
> 11월 21일 ~ 12월 05일 까지 배운 Java를 통해 구현한 notepad입니다.

* SIST강의를 통하여 학습한 JAVA를 가지고 진행한 Projetct입니다.

* JAVA언어를 공부한 것으로 무엇이든 만들어보고 싶어 진행하게 되었습니다.

* Windows OS의 notepad를 참조하여 만들었습니다.

---

### notepad 구현할 기능

1. 새 글

2. 열기

3. 저장

4. 새 이름으로 저장

5. 닫기

6. 메모장 서식 (Java가 제공하는 서식들만 사용)

7. 메모장 정보

---

### CODE 작성 규칙

* Code를 작성함에 있어 되도록 스스로 작성하려고 하며(수업을 듣고 정리한 내용들을 참조하여) 타인이 작성한 code를 참조하지 않고 작성하는 것이 목표

* 재 사용이 되는 code들은 method를 생성하여 작성한다. (되도록 하나의 method는 하나의 일만)

* 처음부터 완벽한 설계는 없으니 작성하면서 계속해서 수정해 나가자.

---

### 주요 class

* JavaMemo

* JavaMemoEvt

* JavaMemoFont

* JavaMemoFontEvt

* MemoHelp

* MemoHelpEvt

* JavaMemoRun

---

## JavaMemo

* 기본적인 Memo장의 gui를 제공하는 class입니다.

* 메모장 화면

    <img src  = https://user-images.githubusercontent.com/74294325/101236013-d22b7580-3710-11eb-862a-de98c50e33b4.png>

* Window의 자식class인 JFrame을 상속받아 기능을 구현하였습니다.<br>
(window를 상속받고 그 안에서 JFrame을 붙이는 것보다 한번에 JFrame을 상속받아 window의 기능들을 사용)

* 메모장의 기본 서식들은 변하지 않는 값이기에 상수로 선언하여 다른 class에서 가져다 쓸 수 있도록 하였습니다.

```java
// 메모장의 기본 서식을 constant로 지정하여 가져다 쓰기 위해 선언
	public static final String[] FONT = { "Dialog", "DialogInput", "Monospaced", "Serif", "SansSerif" };
	public static final String[] STYLENAME = { "PLAIN", "BOLD", "ITALIC", "BOLDITALIC" };
	public static final int[] STYLE = { Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC };
	// 글시 Sizesms 8~100까지 설정하기 위한 배열
	public static final int[] SIZE = new int[100 - 12 + 1];
```

* Style는 STYLENAME와 STYLE로 생성하여 font를 생성할 때는 Font class의 constant가 필요하기에 배열의 index를 맞춰주었습니다.

* JavaMemoEvt와 Has-A 관계를 설정하여 event는 JavaMemoEvt에서 처리

```java
JavaMemoEvt jme = new JavaMemoEvt(this);	
```

---

## JavaMemoEvt

* ActionListener inteface를 구현하여 event처리

* WindowAdapter 상속받아 x버튼 기능 구현

* JavaMemo와 Has-A 관계를 맺기위한 instance 생성 및 생성자 매개변수에 JavaMemo를 넣어줌.

```java
private JavaMemo jm;

public JavaMemoEvt(JavaMemo jm) {
		this.jm = jm;
}// JavaMemoEvt
```

* 파일이 열렸었는지를 판단하는 flag 변수를 instnace 변수로 선언

```java
private boolean openFlag;
```

* 현재 열려잇는 file을 알기 위한 file 변수

```java
// 현재 열려있는 텍스트 파일
private File file;
```

* 현재 열려있는 파일의 초기 상태를 저장할 변수
```java
private String memo;
```


---

* 새 글

    1. 파일이 열린적이 없고 메모장의 내용이 있을때 새 글이 눌린경우
    
    ```java
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
    ```            

    2. 파일이 열린적이 있을경우 <br>
    (초기 상태와 동일한지 확인 후 저장여부를 묻는다.) 
    
    ```java
    } else if (openFlag) {
		// 파일을 열었을때의 초기상태와 동일한지?
		if (memo.equals(jm.getJtaMemo().getText())) { // 파일이 열렸을 때와 동일한지 비교
			// 동일할 시 메모장 초기화
			resetTitle_resetText();
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
		 // end else
    }// end if
    ```
---

* 열기

    1. 파일이 열린 적이 없고 열기 버튼이 눌렸을 경우

        * 내용이 있는 상태에서 열기 버튼이 눌린경우와 내용이 없는 상태에서 눌린 두가지 경우가 있다.

    ```java
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
    ```

    2. 파일이 열린적이 있을경우 <br>
    (초기 상태와 동일한지 확인 후 저장여부를 묻는다.) 

    ```java
    else if (openFlag) {
		// 파일을 열었을때의 초기상태와 동일한지?
		if (memo.equals(jm.getJtaMemo().getText())) { // 파일을 열었을 때의 초기상태와 비교
			// 동일할 시 열기 기능 제공
			openFile();
			// 파일을 열었을때의 초기상태와 동일하지 않을경우.
		} else {
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
		} // end else
    } // end if
    ```

---

* 저장 

    1. 파일이 열린적이 없고 저장버튼이 눌린경우

    ```java
    //메모장에 내용이 있던 없던 새이름으로 저장을 제공한다.
    if (!openFlag && (((jm.getJtaMemo().getText().isEmpty())) || (!jm.getJtaMemo().getText().isEmpty()))) {
		newSave();
    ```

    2. 파일이 열린 적이 있을 경우

    ```java
    //파일을 열었을때의 초기상태와 동일거나 그렇지 않더라도 현재 파일에 저장
    else if (openFlag) {
		save(this.file);
		//저장 후 현재 상태에 메모장내용을 저장
		memo = jm.getJtaMemo().getText();
	} // end if

---

* 새 이름으로 저장

    * 눌리면 새이름으로 저장 기능을 제공한다.

    ```java
    newSave();
    ```

---

* 닫기

    1. 파일이 열린적이 없고 메모장의 내용이 있을때 닫기가 눌린경우

        * 저장여부를 묻고 사용자의 응답에 따른 프로그램 실행

    ```java
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
    ```

    2. 파일이 열린적이 있는경우

    ```java
    else if (openFlag) {
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
    } //end if

---  

* resetText()

    * 메모장의 내용을 초기화 하는 method

    ```java
    public void resetText() {
		jm.getJtaMemo().setText("");
	}// resetText
    ```

---

* resetTitle_resetText()

    * 메모장의 내용을 초기화 하고 title를 초기 상태로 돌리는 method
    
    ```java
    public void resetTitle_resetText() {
		resetText();
		jm.setTitle("제목없음.txt");
	}
    ```

---

* openFile()

    * FileDialog LOAD기능을 제공하여 파일의 경로 및 파일의 이름을 얻어낸다.

    * 얻어낸 경로,이름으로 file을 생성하여 메모장에 파일의 내용을 write한다.

    * openFile로 생성된 file을 instance변수로 선언된 file로 값 할당.

    * FileNotExcption은 FileDialog에서 파일이 선택되었을 시에만 if문을 타게 작성하였기에 예외 처리에서 IOException만 처리하였다.

    ```java
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
    ```

---

* newSave()

    * 새이름으로 저장 기능을 제공하는 method 

    * 새이름으로 저장 후 저장한 파일이 메모장에 상태일 테니 생성된 file을 instance변수로 선언된 file에 값 할당

    ```java
    public void newSave() {
		FileDialog fd = new FileDialog(jm, "저장", FileDialog.SAVE);
		fd.setVisible(true);
		if (fd.getFile() != null) {
            //새이름으로 저장할 파일 생성
			File newfile = new File(fd.getDirectory() + fd.getFile());
			this.file = newfile;
			save(newfile);
			jm.setTitle("자바-메모장[저장 : " + fd.getFile() + "]");
		} // newSave
	}// newSave
    ```

---

* save(File file)

    * 매개변수로 들어오는 파일의 현 상태를 저장하는 method

    ```java
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
    ```

---

* askNewFileSave()

    * 현재 메모장의 내용을 새 파일에 저장할지 묻는 method

    * 반환 값으로 JOptionPane.showConfirmDialog의 값을 반환

    ```java
    public int askNewFileSave() {
		int option = JOptionPane.showConfirmDialog(jm, "현재 메모장의 내용을 새 파일에 저장하시겠습니까?");
		return option;
	}// askNewFileSave
    ```

---

* askOverwrite()

    * 현재 메모장의 내용을 기존 파일에 덮어쓸것인지 묻는 method

    * 반환 값으로 JOptionPane.showConfirmDialog의 값을 반환

    ```java
    public int askOverwrite() {
		int option = JOptionPane.showConfirmDialog(jm,
				"현재 메모장의 내용을 " + this.file.getAbsolutePath()+ " 위치에 저장하시겠습니까?");
		return option;
	}//
    ```

---

* 글꼴 및 메모장 정보를 누르면 Dialog열기

```java
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
```

---

## JavaMemoFont

* JDialog 를 상속받아 메모장의 서식기능을 제공한다.

* JavaMemoFontEvt와 Has-A 관계를 설정하여 event는 JavaMemoFontEvt에서 처리

```java
JavaMemoFontEvt jmfe = new JavaMemoFontEvt(jm,this);
```

* 서식 화면

    <img src = https://user-images.githubusercontent.com/74294325/101236016-dbb4dd80-3710-11eb-9a4c-76d10b107732.png>

---

## JavaMemoFontEvt

* JavaMemo,JavaMemoFont와 Has-A 관계를 맺기위한 instance 생성 및 생성자 매개변수에 JavaMemo, JavaMemoFont를 넣어줌.

* ActionListener,ListSelectionListener inteface를 구현하여 event처리

* WindowAdapter 상속받아 x버튼 기능 구현

```java
//Has - A 관계를 위한 instance 선언
private JavaMemoFont jmf;
private JavaMemo jm;

//Has-A 관계 맺기
public JavaMemoFontEvt(JavaMemo jm, JavaMemoFont jmf) {
	this.jmf = jmf;
	this.jm = jm;
}//JavaMemoFontEvt

```
	
* JList에서 선택될 때마다  preview의 서식을 바꾸기 위한 변수선언

```java
//JList에서 선택될 때마다 preview서식을 바꾸기 위한 preview 서식변수 선언
private String font;
private int style;
private int size;
```

* JList event처리

```java
//ListSelectionEvent는 두번 실행되기 때문에 1번실행으로 제어하기 위한 변수 선언
private boolean flag;
@Override
public void valueChanged(ListSelectionEvent le) {

	//preview 기본 서식 가져와서 값 할당
	font = jmf.getPreviewFont();
	style = jmf.getPreviewStyle();
	size = jmf.getPreviewSize();
		
	//JList가 눌릴때마다 preview의 값이 실시간 변경되기 위한 이벤트들
		
	if(le.getSource() == jmf.getJlFont()) {
		font = JavaMemo.FONT[jmf.getJlFont().getSelectedIndex()];
		jmf.getJtfFont().setText(jmf.getDlmFont().get(jmf.getJlFont().getSelectedIndex()));
		jmf.getJiblPreview().setFont(new Font(font,style,size));
	}//end if

	if(le.getSource() == jmf.getJlStyle()) {
		style = JavaMemo.STYLE[jmf.getJlStyle().getSelectedIndex()];
		jmf.getJtfStyle().setText(jmf.getDlmStyle().get(jmf.getJlStyle().getSelectedIndex()));
		jmf.getJiblPreview().setFont(new Font(font,style,size));
	}//end if
		
	if(le.getSource() == jmf.getJlSize()) {
		size = JavaMemo.SIZE[(int)(jmf.getJlSize().getSelectedIndex())];
		jmf.getJtfSize().setText(String.valueOf(jmf.getDlmSize().get(jmf.getJlSize().getSelectedIndex())));
		jmf.getJiblPreview().setFont(new Font(font,style,size));
	}//end if
	
    //한번만 실행하기 위해 flag변수 뒤집기
    flag = !flag;
	}//valueChanged

```

* ComboBox 및 preview event처리

```java
@Override
public void actionPerformed(ActionEvent ae) {

	//ComboBox의 값이 변경될 때마다 preview text변경 이벤트
	if(ae.getSource() == jmf.getJcbScript()) {
		String flag = jmf.getDjcbScript().getElementAt(jmf.getJcbScript().getSelectedIndex());
		if("한글".equals(flag)) {
			jmf.getJiblPreview().setText("AaBbCc가나다");
	    }//end if
		if("영어".equals(flag)) {
			jmf.getJiblPreview().setText("AaBbCc");
		}//end if
	}//end if
		
	//Font Dialog 닫기 이벤트
	if(ae.getSource() == jmf.getJbtnClose()) {
		jmf.dispose();
	}//end if
		
	//Font Dialog 적용 이벤트
    //현재 미리보기의 서식을 메모장의 서식으로 적용
	if(ae.getSource() == jmf.getJbtnApply()) {
		jm.getJtaMemo().setFont(new Font(font, style, size));
		jmf.dispose();
	}//end if
		
	}//actionPerformed
```

## MemoHelp

* JDialog 를 상속받아 메모장의 도움말 기능을 제공한다.

* MemoHelpEvt와 Has-A 관계를 설정하여 event는 MemoHelpEvt에서 처리

```java
MemoHelpEvt mhe = new MemoHelpEvt(this);
```

* 메모장 정보 화면

    <img src = https://user-images.githubusercontent.com/74294325/101236340-d4db9a00-3713-11eb-9418-9bb6e3c45972.png>

---

## MemoHelpEvt

* MemoHelp와 Has-A 관계를 맺기위한 instance 생성 및 생성자 매개변수에 MemoHelp를 넣어줌.

* ActionListener inteface를 구현하여 event처리

* WindowAdapter 상속받아 x버튼 기능 구현

```java
//Has - A 관계 맺기 위한 Instance 생성
private MemoHelp mh;

//Has - A 관계 맺기
public MemoHelpEvt(MemoHelp jmi) {
	this.mh = jmi;
}//JavaMemoInfoEvt
```

---

## 느낀점

* 먼저는 배운 내용으로 어떠한 것에 결과물이 나온다는 것이 굉장히 뿌듯했다.

* 메모장을 JAVA를 이용해 구현한 것으로 인해 자신감이 조금 생긴가 같다.

* 수업내용을 전부 기억할 수는 없다. 기록하고 필요할 때 찾을 수 있는 환경을 만들자.


---

## 수정사항

* 2020-12-11 (강사님께 코드 리뷰)

	1. Instance 변수를 사용할 때 나는 local변수와 구분하기 위해 this를 붙여서 사용하였다. 하지만 강사님께서는 instance안에서 유일한 변수이면 this를 쓰지 않는 편이 좋다고 말씀하셨다.

		* this를 사용하면 동일한 변수명이 있는지를 확인해야 할 수도 있어 혼란을 야기.

	2. API를 참고하여 사용한 method에서 Exception을 throws하고 있다면 method를 호출한 곳에서 try~catch를 해주는 것이 복잡도를 줄일 수 있다. (꼭 이게 정답이라는 것이 아닌 대부분 사용을 그렇게 한다는 말씀하셨다.)

* code 수정

	* Font Dialog에서 각 Font , Style, Size를 선택할 때마다 각각의 값이 초기화 되는 오류를 발견

	* 수정 전 code 
		
		<img src = https://user-images.githubusercontent.com/74294325/101882353-21721a00-3bd9-11eb-8480-bd9328e88ced.png>

		* font,style,size의 값을 넣어주는 code
			```java
			font = jmf.getPreviewFont();
			style = jmf.getPreviewStyle();
			size = jmf.getPreviewSize();
			```
		* 이 코드들이 valueChanged()안에 들어 있어서 한번 method를 호출 할 때마다 2번 값을 할당하여 초기화가 진행되고 있었다.

	* 수정 후

		<img src = https://user-images.githubusercontent.com/74294325/102028080-aaf93600-3deb-11eb-9340-82e00da99952.PNG>


		* font,style,size의 값을 넣어주는 code
			```java
			font = jmf.getPreviewFont();
			style = jmf.getPreviewStyle();
			size = jmf.getPreviewSize();
			```
		* 이 코드들을 생성자 안으로 넣어 한번만 호출되게 하여 nullpointerexception을 피하면서 두번 실행되지 않는 코드로 바꿈.