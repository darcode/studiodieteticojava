package common;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

/**
 * 
 */
public class Utils implements ICommonConstants {

	// fonts
	private static HashMap m_FontMap = new HashMap();

	private static HashMap m_FontToBoldFontMap = new HashMap();

	// Color support
	private static HashMap m_ColorMap = new HashMap();

	public static Font getFont(String name, int height, int style) {
		String fullName = name + "|" + height + "|" + style;
		Font font = (Font) m_FontMap.get(fullName);
		if (font == null) {
			font = new Font(Display.getCurrent(), name, height, style);
			m_FontMap.put(fullName, font);
		}
		return font;
	}

	public static Font getBoldFont(Font baseFont) {
		Font font = (Font) m_FontToBoldFontMap.get(baseFont);
		if (font == null) {
			FontData fontDatas[] = baseFont.getFontData();
			FontData data = fontDatas[0];
			font = new Font(Display.getCurrent(), data.getName(), data
					.getHeight(), SWT.BOLD);
			m_FontToBoldFontMap.put(baseFont, font);
		}
		return font;
	}

	public static Color getColor(int r, int g, int b) {
		return getColor(new RGB(r, g, b));
	}

	public static Color getColor(RGB rgb) {
		Color color = (Color) m_ColorMap.get(rgb);
		if (color == null) {
			Display display = Display.getCurrent();
			color = new Color(display, rgb);
			m_ColorMap.put(rgb, color);
		}
		return color;
	}

	public static Font getStandardBoldFont() {
		return Utils.getFont("arial", 12, SWT.BOLD);
	}

	public static Font getStandardFont() {
		return Utils.getFont("arial", 12, SWT.NONE);
	}

	public static Color getStandardWhiteColor() {
		return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	}

	public static Color getStandardBackgroundColor() {
		return Utils.getColor(30, 20, 100);
	}

	public static Color getStandardBlackColor() {
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}

	public static void pressKey(Integer key) {
		ArrayList<Integer> keys = new ArrayList<Integer>();
		keys.add(key);
		pressKey(keys);
	}

	public static void pressKey(ArrayList<Integer> keys) {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		if (keys.size() == 1) {
			robot.keyPress(keys.get(0));
			robot.keyRelease(keys.get(0));
		} else if (keys.size() == 2) {
			robot.keyPress(keys.get(0));
			robot.keyPress(keys.get(1));
			robot.keyRelease(keys.get(1));
			robot.keyRelease(keys.get(0));
		} else if (keys.size() == 3) {
			robot.keyPress(keys.get(0));
			robot.keyPress(keys.get(1));
			robot.keyPress(keys.get(2));
			robot.keyRelease(keys.get(2));
			robot.keyRelease(keys.get(1));
			robot.keyRelease(keys.get(0));
		} else if (keys.size() == 4) {
			robot.keyPress(keys.get(0));
			robot.keyPress(keys.get(1));
			robot.keyRelease(keys.get(1));
			robot.keyPress(keys.get(2));
			robot.keyRelease(keys.get(2));
			robot.keyPress(keys.get(3));
			robot.keyRelease(keys.get(3));
			robot.keyRelease(keys.get(0));
		}
	}

	public static String dateAsString(Date date, String pattern) {
		String formatDate = new String();
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			formatDate = format.format(date);
		}
		return formatDate;
	}

	public static String absoluteFilename(String filename) {
		return filename.substring(0, filename.lastIndexOf("."));
	}

	public static String getActiveViewId() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IViewReference[] views = workbench.getActiveWorkbenchWindow()
				.getActivePage().getViewReferences();
		return views[0].getId();
	}

	public static IViewPart getActiveView() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IViewReference[] views = workbench.getActiveWorkbenchWindow()
				.getActivePage().getViewReferences();
		if (views.length > 0)
			return views[0].getView(false);
		else
			return null;
	}

	public static Boolean isActiveView(String viewId) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IViewReference[] views = workbench.getActiveWorkbenchWindow()
				.getActivePage().getViewReferences();
		return views[0].getId().equals(viewId);
	}

	public static void showView(String viewId) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IViewReference[] views = workbench.getActiveWorkbenchWindow()
				.getActivePage().getViewReferences();
		if (views.length == 0 || !views[0].getId().equals(viewId)) {
			workbench.getActiveWorkbenchWindow().getActivePage()
					.resetPerspective();

			try {
				workbench.getActiveWorkbenchWindow().getActivePage().showView(
						viewId);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
			getActiveView().setFocus();
		}
	}

	public static void showViewNotUnique(String viewId) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IViewReference[] views = workbench.getActiveWorkbenchWindow()
				.getActivePage().getViewReferences();
		if (views.length == 0 || !views[0].getId().equals(viewId)) {
			// workbench.getActiveWorkbenchWindow().getActivePage()
			// .resetPerspective();

			try {
				workbench.getActiveWorkbenchWindow().getActivePage().showView(
						viewId);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
			getActiveView().setFocus();
		}
	}

	public static void showMainView() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		try {
			IPerspectiveDescriptor perspDesc = workbench
					.getActiveWorkbenchWindow().getActivePage()
					.getPerspective();
			workbench.getActiveWorkbenchWindow().getActivePage()
					.closePerspective(perspDesc, false, false);
			workbench.showPerspective(ICommonConstants.mainPerspectiveID,
					workbench.getActiveWorkbenchWindow());
		} catch (WorkbenchException we) {
			we.printStackTrace();
		}
	}

	public static String getCDDVDPath() {
		File path = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\"));
		try {
			fileChooser.changeToParentDirectory();
			FileSystemView fileSystem = fileChooser.getFileSystemView();
			for (File item : fileSystem.getFiles(fileChooser
					.getCurrentDirectory(), true)) {
				String descr = fileSystem.getSystemTypeDescription(item);
				if (descr.contains("CD")) {
					path = item;
				}
			}
		} catch (IndexOutOfBoundsException ioobe) {
			ioobe.printStackTrace();
		}
		return path.getAbsolutePath();

	}

	public static void showMessageError(String message) {
		Shell parent = new Shell();
		parent.setSize(200, 50);
		MessageBox msg = new MessageBox(parent);
		msg.setMessage(message);
		msg.setText("Errore applicativo");
		msg.open();
	}

	public static void showMessageInfo(String message) {
		Shell parent = new Shell();
		parent.setSize(200, 50);
		MessageBox msg = new MessageBox(parent);
		msg.setMessage(message);
		msg.setText("Info");
		msg.open();
	}

	public static Image getImageFromFile(String fileName) {
		ImageDescriptor imgDesc = studiodietetico.Activator.getImageDescriptor(fileName);
		final Image img = imgDesc.createImage();
		return img;
	}
}
