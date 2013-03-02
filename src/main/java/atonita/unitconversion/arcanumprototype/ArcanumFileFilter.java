package atonita.unitconversion.arcanumprototype;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ArcanumFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		
		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals(new String("arc"))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return new String("Arcanum tomes.");
	}

	/**
	 * Private utiltiy used to get the extension of a file.
	 * @param f a <code>File</code>.
	 * @return the extension of the supplied file.
	 */
	private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

}
