package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controllers.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A view representing a square on the game board.
 */
public class SquareView extends JPanel {
    private final Color borderColor = new Color(0,0,0);
    /**
     * Constructs a SquareView with the specified square ID, color, piece, and controller.
     *
     * @param squareId   The ID of the square.
     * @param color      The background color of the square.
     * @param piece      The piece image associated with the square.
     * @param controller The controller for handling square click events.
     */
    public SquareView(int squareId, Color color, String piece, Controller controller) {
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(borderColor, 1));
        setPieceImage(piece);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller != null) {
                    controller.handleSquareClick(squareId);
                }
            }
        });
    }

    /**
     * Sets the piece image for the square.
     *
     * @param piece The filename of the piece image.
     */
    public void setPieceImage(String piece) {
        if (!piece.isEmpty()) {
            try {
                BufferedImage pieceImg = ImageIO.read(getClass().getResourceAsStream("/pieces_icons/" + piece + ".png"));
                JLabel pieceImgLabel = new JLabel(new ImageIcon(pieceImg));
                add(pieceImgLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
