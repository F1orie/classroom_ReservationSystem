package client_system;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class SelfReservationDialog extends Dialog implements ActionListener, WindowListener {

    private ReservationControl rc;
    private ChoiceReservation choiceReservation;
    private Button buttonConfirm, buttonCancel;

    public SelfReservationDialog(Frame parent, ReservationControl rc) {
        super(parent, "自己予約確認 : 検索フィルター", true);
        this.rc = rc;
        this.choiceReservation = new ChoiceReservation();

        // レイアウト設定
        setLayout(new BorderLayout());

     // 北パネル（教室・日付）
        Panel panelNorth = new Panel(new FlowLayout());
        panelNorth.add(new Label("教室"));
        panelNorth.add(choiceReservation.getComboBoxFacility());
        panelNorth.add(new Label("日付"));
        panelNorth.add(choiceReservation.getComboBoxDate());
        add(panelNorth, BorderLayout.NORTH);

        // 中央パネル（開始・終了時間）
        Panel panelCenter = new Panel(new FlowLayout());
        panelCenter.add(new Label("利用開始時間"));
        panelCenter.add(choiceReservation.getComboBoxStartTime());
        panelCenter.add(new Label("利用終了時間"));
        panelCenter.add(choiceReservation.getComboBoxEndTime());
        add(panelCenter, BorderLayout.CENTER);


        // 南パネル（ボタン）
        Panel panelSouth = new Panel(new FlowLayout());
        buttonCancel = new Button("キャンセル");
        buttonConfirm = new Button("確認");
        panelSouth.add(buttonCancel);
        panelSouth.add(buttonConfirm);
        add(panelSouth, BorderLayout.SOUTH);

        // イベント設定
        buttonConfirm.addActionListener(this);
        buttonCancel.addActionListener(this);
        addWindowListener(this); // ← 「×」ボタン対応

        // コンボボックス初期化
        initializeComboBoxes();

        // ウィンドウ設定
        setBounds(100, 100, 400, 150);
        setResizable(false);
    }

    private void initializeComboBoxes() {
        List<String[]> reservationData = rc.getSelfReservations(rc.getLoginUserId());

        List<String> facilityList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        List<String> startTimeList = new ArrayList<>();
        List<String> endTimeList = new ArrayList<>();

        for (String[] record : reservationData) {
            String date = record[0];
            String facility = record[1];
            String start = record[2];
            String end = record[3];

            if (!facilityList.contains(facility)) facilityList.add(facility);
            if (!dateList.contains(date)) dateList.add(date);
            if (!startTimeList.contains(start)) startTimeList.add(start);
            if (!endTimeList.contains(end)) endTimeList.add(end);
        }

        // 先頭に空白を追加して「未選択」検索を可能に
        choiceReservation.setFacilityItems(prependEmpty(facilityList));
        choiceReservation.setDateItems(prependEmpty(dateList));
        choiceReservation.setStartTimeItems(prependEmpty(startTimeList));
        choiceReservation.setEndTimeItems(prependEmpty(endTimeList));
    }

    private List<String> prependEmpty(List<String> list) {
        List<String> result = new ArrayList<>();
        result.add("");  // 未選択を許容
        result.addAll(list);
        return result;
    }

    private void updateReservationList() {
        String facility = choiceReservation.getComboBoxFacility().getSelectedItem();
        String date = choiceReservation.getComboBoxDate().getSelectedItem();
        String start = choiceReservation.getComboBoxStartTime().getSelectedItem();
        String end = choiceReservation.getComboBoxEndTime().getSelectedItem();

        List<String> resultList = rc.searchSelfReservations(rc.getLoginUserId(), facility, date, start, end);

        Frame owner = (Frame) getOwner();
        if (owner instanceof MainFrame mf) {
            if (resultList.isEmpty()) {
                mf.ReservationStatusText.setText("予約情報が見つかりません");
            } else {
                StringBuilder sb = new StringBuilder();
                for (String row : resultList) {
                    sb.append(row).append("\n");
                }
                mf.ReservationStatusText.setText(sb.toString());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonConfirm) {
            updateReservationList();
        } else if (e.getSource() == buttonCancel) {
            dispose();
        }
    }

    // --- WindowListener 実装（×ボタン対応） ---
    @Override
    public void windowClosing(WindowEvent e) {
        dispose();
    }

    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}
}
