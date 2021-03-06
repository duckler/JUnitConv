package com.tecnick.junitconv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Rectangle;

/**
 * Title: GroupBox Class<br>
 * Description: Is a Bordered Panel with optional title<br>
 *
 * <br/>Copyright (c) 2002-2006 Tecnick.com S.r.l (www.tecnick.com) Via Ugo
 * Foscolo n.19 - 09045 Quartu Sant'Elena (CA) - ITALY - www.tecnick.com -
 * info@tecnick.com <br/>
 * Project homepage: <a href="http://junitconv.sourceforge.net" target="_blank">http://junitconv.sourceforge.net</a><br/>
 * License: http://www.gnu.org/copyleft/gpl.html GPL 2
 * 
 * @author Nicola Asuni [www.tecnick.com].
 * @version 1.0.004
 */
public class GroupBox extends Panel {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8953327533987753026L;
	
	/**
	 * Applet title.
	 */
	protected String title = null;
	
	/**
	 * Minimum distance between border and component edge.
	 */
	private int border_padding = 2; //
	
	/**
	 * Minimum distance between border title text.
	 */
	private int text_padding = 2; //
	
	/**
	 * Border width.
	 */
	private int border_width = 2; //
	
	/**
	 * Text color (foreground color).
	 */
	private Color textColor;
	
	/**
	 * Background color.
	 */
	private Color backgroundColor;
	
	/**
	 * Font for title.
	 */
	private Font title_font;
	
	/**
	 * Default font.
	 */
	private Font default_font;
	
//	-----------------------------------------------------------------------------
	
	/**
	 * constructor.
	 * @param ti the title of the GroupBox. To be used if you want a title
	 * @param bw border width
	 * @param bp border padding
	 * @param tp text padding
	 * @param tf title font
	 */
	public GroupBox(String ti, int bw, int bp, int tp, Font tf) {
		setBorderWidth(bw);
		setBorderPadding(bp);
		setTextPadding(tp);
		setTitleFont(tf);
		title = ti;
	}
//	-----------------------------------------------------------------------------
	/**
	 * void constructor. To be used if you don't want a title.
	 */
	public GroupBox(){
		setTitleFont(getFont());
	}
//	-----------------------------------------------------------------------------
	/**
	 * Changes the GroupBox's title.
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title=title;
		repaint();
	}
//	-----------------------------------------------------------------------------
	/**
	 * Changes the GroupBox's title.
	 * @param tf the new title
	 */
	public void setTitleFont(Font tf) {
		title_font = tf;
		default_font = getFont();
		repaint();
	}
//	-----------------------------------------------------------------------------
	/**
	 * Set the border width
	 * @param bw border width
	 */
	public void setBorderWidth(int bw) {
		border_width = bw;
		if ((border_width % 2) > 0) {
			border_width += 1;
		}
		repaint();
	}
//	-----------------------------------------------------------------------------
	/**
	 * Set the border padding (minimum distance between border and component)
	 * @param bp border padding
	 */
	public void setBorderPadding(int bp) {
		border_padding = bp;
		repaint();
	}
//	-----------------------------------------------------------------------------
	/**
	 * Set the padding around text title
	 * @param tp border padding
	 */
	public void setTextPadding(int tp) {
		text_padding = tp;
		repaint();
	}
//	-----------------------------------------------------------------------------
	/**
	 * Redefines the inherited method to allow space for drawing the border
	 * @return Inset
	 */
	public Insets insets(){
		int addspace = border_padding + border_width;
		int topBorder = addspace;
		if (title != null){
			FontMetrics fMetrics = getFontMetrics(title_font);
			int title_height = border_width + fMetrics.getMaxAscent() + fMetrics.getMaxDescent();
			int half_title = (int) Math.ceil(title_height/2);
			if (half_title > (border_padding - text_padding)) {
				topBorder = title_height + text_padding + border_width;
			}
			else {
				topBorder = half_title + border_padding + border_width;
			}
		}
		return(new Insets(topBorder, addspace, addspace, addspace));
	}
//	-----------------------------------------------------------------------------
	/**
	 * Redefines the inherited getMinimumSize() method for better sizing
	 * @return dimension
	 */
	public Dimension getMinimumSize() {
		Dimension superdim = super.getMinimumSize();
		if (title != null){
			FontMetrics fMetrics = getFontMetrics(title_font);
			int minWidth = fMetrics.stringWidth(title) + (4 * border_width) + (2 * text_padding) + (3 * border_padding);
			int newWidth = Math.max(superdim.width, minWidth);
			int newHeight1 = border_padding + (3 * border_width) + text_padding + fMetrics.getMaxAscent() + fMetrics.getMaxDescent();
			int newHeight2 = (2 * (border_padding + border_width)) + fMetrics.getMaxAscent() + fMetrics.getMaxDescent();
			int newHeight = Math.max(newHeight1, newHeight2);
			newHeight = Math.max(superdim.height, newHeight);
			return new Dimension(newWidth, newHeight);
		}
		else {
			return superdim;
		}
	}
//	-----------------------------------------------------------------------------
	/**
	 * Redefines the inherited method to draw the border and title in
	 * addition to inherited functionality.
	 * @param g the Graphics object
	 */
	public void paint(Graphics g){
		super.paint(g);
		Rectangle boxBounds = getBounds();
		
		textColor = super.getForeground();
		backgroundColor = super.getBackground();
		FontMetrics fMetrics = getFontMetrics(title_font);
		
		int half_bw = (int) Math.round(border_width/2); // 1/2 border width
		
		int x1 = 0;
		int y1 = 0;
		int x2 = boxBounds.width-1;
		int y2 = boxBounds.height-1;
		
		if (title != null){
			int title_height = border_width + fMetrics.getMaxAscent() + fMetrics.getMaxDescent();
			y1 = (int) Math.ceil(title_height/2);
		}
		
		//draw external border
		DrawBorder(g, half_bw, x1, y1, x2, y2);
		
		if (title != null){
			int title_height = border_width + (2 * text_padding) + fMetrics.getMaxAscent() + fMetrics.getMaxDescent();
			y1 = (int) Math.ceil(title_height/2);
			
			int stringWidth = fMetrics.stringWidth(title);
			
			int xt = border_width + (2 * border_padding);
			int yt = 0;
			int wt = stringWidth + (2 * (text_padding + border_width));
			int ht = fMetrics.getMaxAscent() + fMetrics.getMaxDescent() + (2 * border_width);
			
			g.setColor(backgroundColor); //set background color
			g.clearRect(xt,yt,wt,ht); //clear title area
			
			g.setColor(textColor);
			g.setFont(title_font); //set title font
			g.drawString(title, xt + border_width + text_padding, yt + border_width + fMetrics.getMaxAscent());
			g.setFont(default_font); //restore default font
			
			//draw border around text
			DrawBorder(g, half_bw, xt, yt, xt + wt, yt + ht);
			//setFont(default_font); //restore default font
		}
	}
//	-----------------------------------------------------------------------------
	/**
	 * Draw a 3D carved border
	 * @param g current graphics
	 * @param bw 1/2 border
	 * @param x1 x origin coordinate
	 * @param y1 y origin coordinate
	 * @param x2 x final coordinate
	 * @param y2 y final coordinate
	 */
	private void DrawBorder(Graphics g, int bw, int x1, int y1, int x2, int y2) {
		Color brightercolor = backgroundColor.brighter();
		Color darkercolor = backgroundColor.darker();
		g.setColor(darkercolor); //dark lines
		for (int i=0; i<border_width; i++) {
			if (i == bw) {
				g.setColor(brightercolor); //bright lines
			}
			g.drawLine(x1+i, y1+i, x2-i-1, y1+i); //top
			g.drawLine(x1+border_width-i, y2-border_width+i, x2-border_width+i, y2-border_width+i); //bottom
			g.drawLine(x1+i, y1+i+1, x1+i, y2-i); //left
			g.drawLine(x2-border_width+i, y1+border_width-i-1, x2-border_width+i, y2-border_width+i); //right
		}
		g.setColor(textColor); //restore text color
	}
//	-----------------------------------------------------------------------------
}//end of class
//=============================================================================
//END OF FILE
//=============================================================================