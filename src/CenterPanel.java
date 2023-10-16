import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    public CellPanel[][] boardCells = new CellPanel[8][8];

    private BoardState boardState;

    private CellPanel selectedCell;

    public CenterPanel(){
        boardState = BoardState.NONE_SELECT;

        this.setLayout(new GridLayout(8, 8));
        boolean isWhite = true;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                CellPanel cellPanel = new CellPanel(isWhite, i, j);
                if(i == 1 || i == 6){
                    cellPanel.AddImage(new ChessPiece(PieceType.PAWN, i == 1 ? PieceColor.BLACK : PieceColor.WHITE));
                }
                if(i == 0 && (j == 0 || j == 7)){
                    cellPanel.AddImage(new ChessPiece(PieceType.ROOK, PieceColor.BLACK));
                }
                if(i == 7 && (j == 0 || j == 7)){
                    cellPanel.AddImage(new ChessPiece(PieceType.ROOK, PieceColor.WHITE));
                }
                if(i == 0 && (j == 1 || j == 6)){
                    cellPanel.AddImage(new ChessPiece(PieceType.KNIGHT, PieceColor.BLACK));
                }
                if(i == 7 && (j == 1 || j == 6)){
                    cellPanel.AddImage(new ChessPiece(PieceType.KNIGHT, PieceColor.WHITE));
                }
                if(i == 0 && (j == 2 || j == 5)){
                    cellPanel.AddImage(new ChessPiece(PieceType.BISHOP, PieceColor.BLACK));
                }
                if(i == 7 && (j == 2 || j == 5)){
                    cellPanel.AddImage(new ChessPiece(PieceType.BISHOP, PieceColor.WHITE));
                }
                if(i == 0 && (j == 3)){
                    cellPanel.AddImage(new ChessPiece(PieceType.QUEEN, PieceColor.BLACK));
                }
                if(i == 7 && (j == 4)){
                    cellPanel.AddImage(new ChessPiece(PieceType.QUEEN, PieceColor.WHITE));
                }
                if(i == 0 && (j == 4)){
                    cellPanel.AddImage(new ChessPiece(PieceType.KING, PieceColor.BLACK));
                }
                if(i == 7 && (j == 3)){
                    cellPanel.AddImage(new ChessPiece(PieceType.KING, PieceColor.WHITE));
                }
                this.add(cellPanel);
                boardCells[i][j] = cellPanel;
                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
        selectedCell = null;
    }

    public void OnClickCellPanel(int x, int y){

        CellPanel clickedCellPanel = boardCells[x][y];
        clickedCellPanel.Select();

        if(boardState == BoardState.NONE_SELECT){
            DeselectAllCells();
            if(clickedCellPanel.currentChessPiece != null){
                //Hien cac nuoc di va nuoc an quan kha dung
                switch (clickedCellPanel.currentChessPiece.type){
                    case PAWN -> {
                        PawnCheck(x, y);
                        break;
                    }
                }
                selectedCell = clickedCellPanel;
                boardState = BoardState.PIECE_SELECT;
            }
            else{ //O trong

            }
        } else if (boardState == BoardState.PIECE_SELECT)
        {
            if(clickedCellPanel.isValidMove){
                //Move
                System.out.println("BoardState.PIECE_SELECT");
                clickedCellPanel.AddImage(selectedCell.currentChessPiece);
                selectedCell.RemovePiece();
                selectedCell = null;
                //Chuyen trang thai ve khong chon
                boardState = BoardState.NONE_SELECT;
                DeselectAllCells();
            }
            else{
                DeselectAllCells();
            }
        }
    }

    public void DeselectAllCells(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                boardCells[i][j].Deselect();
            }
        }
    }

    private void PawnCheck(int x, int y){
        ChessPiece thisPiece = boardCells[x][y].currentChessPiece;
        if(thisPiece.color == PieceColor.WHITE){
            System.out.println("WHITE");
            int maxMove = (x == 6) ? 2 : 1;
            for(int i = x - 1; i >= x - maxMove; i--){
                System.out.println(CheckValidCoordinate(i, y));
                if(!CheckValidCoordinate(i, y)) break;
                ChessPiece _chessPiece = boardCells[i][y].currentChessPiece;
                if(_chessPiece != null){ //Co ton tai quan co
                    break;
                }
                else{
                    boardCells[i][y].SetColor(true);
                }
            }
            if(CheckValidCoordinate(x - 1, y - 1)){ //Cheo tren trai
                CellPanel _cellPanel = boardCells[x - 1][y - 1];
                if(_cellPanel.currentChessPiece != null){
                    if(_cellPanel.currentChessPiece.color != thisPiece.color){
                        _cellPanel.SetColor(false);
                    }
                }
            }
            if(CheckValidCoordinate(x - 1, y + 1)){ //Cheo tren phai
                CellPanel _cellPanel = boardCells[x - 1][y + 1];
                if(_cellPanel.currentChessPiece != null){
                    if(_cellPanel.currentChessPiece.color != thisPiece.color){
                        _cellPanel.SetColor(false);
                    }
                }
            }
        }
        else{
            System.out.println("BLACK");
            int maxMove = (x == 1) ? 2 : 1;
            for(int i = x + 1; i <= x + maxMove; i++){
                if(!CheckValidCoordinate(i, y)) break;
                ChessPiece _chessPiece = boardCells[i][y].currentChessPiece;
                if(_chessPiece != null){ //Co ton tai quan co
                    break;
                }
                else{
                    boardCells[i][y].SetColor(true);
                }
            }
            if(CheckValidCoordinate(x + 1, y - 1)){ //Cheo tren trai
                CellPanel _cellPanel = boardCells[x + 1][y - 1];
                if(_cellPanel.currentChessPiece != null){
                    if(_cellPanel.currentChessPiece.color != thisPiece.color){
                        _cellPanel.SetColor(false);
                    }
                }
            }
            if(CheckValidCoordinate(x + 1, y + 1)){ //Cheo tren phai
                CellPanel _cellPanel = boardCells[x + 1][y + 1];
                if(_cellPanel.currentChessPiece != null){
                    if(_cellPanel.currentChessPiece.color != thisPiece.color){
                        _cellPanel.SetColor(false);
                    }
                }
            }
        }
    }

    private boolean CheckValidCoordinate(int n){
        return n >= 0 && n < 8;
    }

    private boolean CheckValidCoordinate(int x, int y){
        return CheckValidCoordinate(x) && CheckValidCoordinate(y);
    }
}
