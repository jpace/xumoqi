package org.incava.xumoqi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

public class MultiLineSendEditText extends EditText {

	public MultiLineSendEditText(Context context) {
		super(context);
	}

	public MultiLineSendEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MultiLineSendEditText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public InputConnection onCreateInputConnection(EditorInfo ei) {
		InputConnection ic = super.onCreateInputConnection(ei);
		ei.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
		return ic;
	}
}
