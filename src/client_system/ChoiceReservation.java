package client_system;

import java.awt.Choice;
import java.util.List;

public class ChoiceReservation {

    private Choice comboBoxFacility;
    private Choice comboBoxDate;
    private Choice comboBoxStartTime;
    private Choice comboBoxEndTime;

    public ChoiceReservation() {
        comboBoxFacility = new Choice();
        comboBoxDate = new Choice();
        comboBoxStartTime = new Choice();
        comboBoxEndTime = new Choice();
    }

    // --- 各コンボボックス取得用メソッド ---
    public Choice getComboBoxFacility() {
        return comboBoxFacility;
    }

    public Choice getComboBoxDate() {
        return comboBoxDate;
    }

    public Choice getComboBoxStartTime() {
        return comboBoxStartTime;
    }

    public Choice getComboBoxEndTime() {
        return comboBoxEndTime;
    }

    // --- 初期化用メソッド ---
    public void setFacilityItems(List<String> facilityList) {
        comboBoxFacility.removeAll();
        for (String item : facilityList) {
            comboBoxFacility.add(item);
        }
    }

    public void setDateItems(List<String> dateList) {
        comboBoxDate.removeAll();
        for (String item : dateList) {
            comboBoxDate.add(item);
        }
    }

    public void setStartTimeItems(List<String> startTimeList) {
        comboBoxStartTime.removeAll();
        for (String item : startTimeList) {
            comboBoxStartTime.add(item);
        }
    }

    public void setEndTimeItems(List<String> endTimeList) {
        comboBoxEndTime.removeAll();
        for (String item : endTimeList) {
            comboBoxEndTime.add(item);
        }
    }
}
