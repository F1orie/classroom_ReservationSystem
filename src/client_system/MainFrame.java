package client_system;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends Frame implements ActionListener, WindowListener{
	ReservationControl	reservationControl;						// ReservationControlクラスのインスタンス生成
	// パネルインスタンスの生成
	Panel	panelNorth;											// 上部パネル
	Panel	panelNorthSub1;										//   @1 上部パネルの上
	Panel	panelNorthSub2;										//   @1 上部パネルの下
	Panel	panelCenter;										// 中央パネル
	Panel	panelSouth;											// @2 下部パネル
	// ボタンインスタンスの生成
	Button	buttonLog;											// ログイン・ログアウトボタン
	Button	buttonExplanation;									// @1 教室概要ボタン
	Button	buttonReservation;									// @2 新規予約ボタン
	Button  buttonCheckReservationStatus;                       // @3 教室予約状況確認ボタン
	// @1 コンボボックスのインスタンス生成
	ChoiceFacility	choiceFacility;								// @1 教室選択用コンボボックス
	// テキストフィールドのインスタンス生成
	TextField	tfLoginID;										// ログインIDを表示するテキストフィールド
	// テキストエリアのインスタンス生成
	TextArea	textMessage;									// 結果表示用メッセージ欄
	TextArea    ReservationStatusText;                          // @3 予約表示用テキストエリア
	
	//// MainFrameコンストラクタ
	public	MainFrame( ReservationControl rc) {
		reservationControl	= rc;								// ReservationControlインスタンス保存

		// ボタンの生成
		buttonLog = new Button( " ログイン ");					// ログインボタン
		buttonExplanation = new Button( "教室概要");			// @1 教室選択ボタン
		buttonReservation = new Button( "新規予約");			// @2 新規予約ボタン
		buttonCheckReservationStatus = new Button("予約状況");  // @3 予約状況確認ボタン

		// @1 教室選択用コンボボックスの生成
		List<String> facilityId = new ArrayList<String>();		// @1 全てのfacilityIDを入れるリスト
		facilityId 		= rc.getFacilityId();					// @1 全facilityIDを読出し，リストに入れる
		choiceFacility	= new ChoiceFacility( facilityId);		// @1 教室選択用コンボボックスのインスタンス生成
		
		// ログインID用表示ボックスの生成
		tfLoginID = new TextField( "未ログイン", 10);
		tfLoginID.setEditable( false);
		
		// 上中下のパネルを使うため，レイアウトマネージャーにBorderLayoutを設定
		setLayout( new BorderLayout());
		
		// @1 上部パネルの上パネルに「教室予約システム」というラベルと【ログイン】ボタン等を追加
		panelNorthSub1 = new Panel();							// @1 NorthSub1のパネルインスタンスを生成
		panelNorthSub1.add( new Label( "教室予約システム　"));	// @1 タイトルラベルを付加
		panelNorthSub1.add( buttonLog);							// @1 ログインボタンを付加
		panelNorthSub1.add( new Label( "　　　　　　ログインID："));	// @1 ログインIDタイトルラベルを付加
		panelNorthSub1.add( tfLoginID);							// @1 ログインID表示用テキストフィールドを付加
																// @1
		// @1 上部パネルの下パネルに教室選択及び教室概要ボタンを追加
		panelNorthSub2 = new Panel();							// @1 NorthSub2のパネルインスタンスを生成
		panelNorthSub2.add( new Label( "教室"));				// @1 教室選択コンボボックスのラベルを付加
		panelNorthSub2.add( choiceFacility);					// @1 教室選択コンボボックスを付加
		panelNorthSub2.add( new Label( "　"));					// @1 コンボボックスとボタンの隙間をラベルで付加
		panelNorthSub2.add( buttonExplanation);					// @1 教室概要表示ボタンを付加
		panelNorthSub2.add( buttonCheckReservationStatus);      // @3 教室予約状況確認ボタンを付加

		// @1 上部パネルに上下2つのパネルを追加
		panelNorth = new Panel( new BorderLayout());			// @1 panelNorthをBorderLayoutのパネルで生成
		panelNorth.add( panelNorthSub1, BorderLayout.NORTH);	// @1 panelNorthSub1を上部に付加
		panelNorth.add( panelNorthSub2, BorderLayout.CENTER);	// @1 panelNorthSub2を下部に付加
																// @1
		add( panelNorth, BorderLayout.NORTH);

		// 中央パネルにテキストメッセージ欄と予約表示用テキストエリアを設定
		panelCenter = new Panel();								// 中央パネルインスタンスを生成
		textMessage = new TextArea( 2, 80);					    // メッセージ表示用テキストエリアインスタンスを生成
		textMessage.setEditable( false);						// テキストエリアをユーザによる編集不可に設定
		panelCenter.add( textMessage);							// 中央パネルにメッセージ表示エリアを付加
		
		ReservationStatusText = new TextArea(18, 80);           // @3 予約表示用テキストエリアの生成
		ReservationStatusText.setEditable(false);               // @3 ユーザーによる編集不可に設定
		panelCenter.add(ReservationStatusText);                 // @3 中央パネルに予約表示エリアを付加
		
		// MainFrameに中央パネルを追加
		add( panelCenter, BorderLayout.CENTER);
		
		// @2 下部パネルに部品を配置
		panelSouth = new Panel();								// @2下部パネルインスタンスを生成
		panelSouth.add( buttonReservation);						// @2 新規予約ボタンを付加
		// @2 MainFrameに下部パネルを追加
		add( panelSouth, BorderLayout.SOUTH);
		
		// Listenerの追加
		buttonLog.addActionListener( this);						// ActionListenerにログインボタンを追加
		buttonExplanation.addActionListener( this);				// @1 ActionListenerに教室概要ボタンを追加
		buttonReservation.addActionListener( this);				// @2 ActionListenerに新規予約ボタンを追加
		buttonCheckReservationStatus.addActionListener(this);   // @3 ActionListenerに教室予約状況確認ボタンを追加
		addWindowListener( this);								// WindowListenerを追加
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit( 0);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String	result = new String();
		// 押下ボタンがログインボタンの時，loginLogoutメソッドを実行
		if( e.getSource() == buttonLog) {
			result = reservationControl.loginLogout( this);
            textMessage.setText(result); // ログイン/ログアウトの結果はtextMessageに表示
            ReservationStatusText.setText(""); // ログイン/ログアウト時は予約状況エリアをクリア
		// @1 押下ボタンが教室概要ボタンの時，getFacilityExplanationメソッドを実行
		} else if( e.getSource() == buttonExplanation) {
			result = reservationControl.getFacilityExplanation( choiceFacility.getSelectedItem());
            textMessage.setText(result); // 教室概要の結果はtextMessageに表示
            ReservationStatusText.setText(""); // 教室概要表示時は予約状況エリアをクリア
		// @2 押下ボタンが新規予約ボタンの時，makeReservationメソッドを実行
		} else if( e.getSource() == buttonReservation) {
			result = reservationControl.makeReservation( this);
            textMessage.setText(result); // 新規予約の結果はtextMessageに表示
            ReservationStatusText.setText(""); // 新規予約時は予約状況エリアをクリア
		// @3 押下ボタンが教室予約状況確認ボタンの時，getReservationStatusメソッドを実行
		} else if (e.getSource() == buttonCheckReservationStatus) {
		    String reservationStatus = reservationControl.getReservationStatus(this); // 予約状況を取得
		    ReservationStatusText.setText(reservationStatus); // 予約状況をReservationStatusTextに表示
            textMessage.setText(""); // 予約状況表示時はメッセージエリアをクリア
		}
	}
}
