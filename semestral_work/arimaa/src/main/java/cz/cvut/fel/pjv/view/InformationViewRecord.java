package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controllers.RecordController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The InformationViewRecord class represents the panel that displays information and buttons for recorded game moves.
 */
public class InformationViewRecord extends JPanel {
    private JLabel title;
    private JButton nextMove;
    private JButton prevMove;

    /**
     * Constructs a new InformationViewRecord object with the specified record controller.
     *
     * @param recordController The record controller object responsible for handling recorded game moves.
     */
    public InformationViewRecord(RecordController recordController) {
        setupPanel();
        createTitle();
        createNextMoveButton();
        createPrevMoveButton();
        addComponents();
        setActionListeners(recordController);
    }

    private void setupPanel() {
        setLayout(null);
        setBounds(490, 0, 296, 463);
        setPreferredSize(new Dimension(300, 500));
    }

    private void createTitle() {
        title = new JLabel("Click to see moves");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setBounds(80, 30, 200, 40);
    }

    private void createNextMoveButton() {
        nextMove = new JButton("Next move");
        nextMove.setPreferredSize(new Dimension(120, 30));
        nextMove.setBounds(160, 100, 120, 30);
    }

    private void createPrevMoveButton() {
        prevMove = new JButton("Previous move");
        prevMove.setPreferredSize(new Dimension(120, 30));
        prevMove.setBounds(20, 100, 120, 30);
    }

    private void addComponents() {
        add(title);
        add(prevMove);
        add(nextMove);
    }

    private void setActionListeners(RecordController recordController) {
        setActionListenerToPrevMove(recordController);
        setActionListenerToNextMove(recordController);
    }

    private void setActionListenerToPrevMove(RecordController recordController) {
        prevMove.addActionListener(e -> recordController.handlePrevMoveBtn());
    }

    private void setActionListenerToNextMove(RecordController recordController) {
        nextMove.addActionListener(e -> recordController.handleNextMoveBtn());
    }
}
