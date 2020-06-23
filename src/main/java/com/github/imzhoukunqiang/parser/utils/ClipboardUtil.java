package com.github.imzhoukunqiang.parser.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * Project sql-parser
 * Created by zkq on 18-5-14 下午9:56.
 */
public class ClipboardUtil {
    /**
     * 把文本设置到剪贴板（复制）
     */
    public static void setClipboardString(String text) { // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // 封装文本内容
        Transferable trans = new StringSelection(text); // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }

    /**
     * 从剪贴板中获取文本（粘贴）
     */
    public static String getClipboardString() { // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // 获取剪贴板中的内容
        Transferable trans = clipboard.getContents(null);
        if (trans != null) { // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try { // 获取剪贴板中的文本内容
                    return (String) trans.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}