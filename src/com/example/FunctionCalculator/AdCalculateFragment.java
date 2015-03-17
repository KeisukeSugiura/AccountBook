package com.example.FunctionCalculator;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;



public class AdCalculateFragment extends Fragment{
	View mView;
	TextView mTextView;
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	Button button6;
	Button button7;
	Button button8;
	Button button9;
	Button button0;
	Button buttonPlus;
	Button buttonMinus;
	Button buttonMul;
	Button buttonDiv;
	Button buttonRA;
	Button buttonLA;
	Button buttonEqual;
	Button buttonClear;

	String buffer;
	Double result = 0.0;
	String operator;
	boolean append = false;
	boolean appendMark = false;
	int appendArk = 0;

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ

		View mView = inflater.inflate(R.layout.cal_image, container, false);

		// 各リスナーの初期化処理

		mTextView = (TextView) mView.findViewById(R.id.buf_text);
		button1 = (Button) mView.findViewById(R.id.button1);
		button2 = (Button) mView.findViewById(R.id.button2);
		button3 = (Button) mView.findViewById(R.id.button3);
		button4 = (Button) mView.findViewById(R.id.button4);
		button5 = (Button) mView.findViewById(R.id.button5);
		button6 = (Button) mView.findViewById(R.id.button6);
		button7 = (Button) mView.findViewById(R.id.button7);
		button8 = (Button) mView.findViewById(R.id.button8);
		button9 = (Button) mView.findViewById(R.id.button9);
		button0 = (Button) mView.findViewById(R.id.button0);
		buttonPlus = (Button) mView.findViewById(R.id.button_plus);
		buttonMinus = (Button) mView.findViewById(R.id.button_minus);
		buttonEqual = (Button) mView.findViewById(R.id.button_equal);
		buttonMul = (Button) mView.findViewById(R.id.button_mul);
		buttonDiv = (Button) mView.findViewById(R.id.button_div);
		buttonLA = (Button) mView.findViewById(R.id.button_la);
		buttonRA =(Button) mView.findViewById(R.id.button_ra);
		buttonClear = (Button) mView.findViewById(R.id.button_clear);

		button1.setOnClickListener(new ButtonListner1());
		button2.setOnClickListener(new ButtonListner2());
		button3.setOnClickListener(new ButtonListner3());
		button4.setOnClickListener(new ButtonListner4());
		button5.setOnClickListener(new ButtonListner5());
		button6.setOnClickListener(new ButtonListner6());
		button7.setOnClickListener(new ButtonListner7());
		button8.setOnClickListener(new ButtonListner8());
		button9.setOnClickListener(new ButtonListner9());
		button0.setOnClickListener(new ButtonListner0());
		buttonPlus.setOnClickListener(new ButtonListnerPlus());
		buttonMinus.setOnClickListener(new ButtonListnerMinus());
		buttonMul.setOnClickListener(new ButtonListnerMul());
		buttonDiv.setOnClickListener(new ButtonListnerDiv());
		buttonLA.setOnClickListener(new ButtonListnerLA());
		buttonRA.setOnClickListener(new ButtonListnerRA());
		buttonEqual.setOnClickListener(new ButtonListnerEqual());
		buttonClear.setOnClickListener(new ButtonListnerClear());

		initBuffer();
		initOperator();

		return mView;
	}

	class ButtonListner1 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if (append) {
				buffer = buffer + "1";
			} else {
				buffer = "1";
			}
			appendMark = true;
			append = true;
			showBuffer();
		}

	}

	class ButtonListner2 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if (append) {
				buffer = buffer + "2";
			} else {
				buffer = "2";
			}
			appendMark=true;
			append = true;
			showBuffer();
		}

	}

	class ButtonListner3 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if (append) {
				buffer = buffer + "3";
			} else {
				buffer = "3";
			}
			appendMark=true;
			append = true;
			showBuffer();
		}

	}

	class ButtonListner4 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if (append) {
				buffer = buffer + "4";
			} else {
				buffer = "4";
			}
			appendMark=true;
			append = true;
			showBuffer();
		}

	}

	class ButtonListner5 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if (append) {
				buffer = buffer + "5";
			} else {
				buffer = "5";
			}
			appendMark=true;
			append = true;
			showBuffer();
		}

	}

	class ButtonListner6 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if (append) {
				buffer = buffer + "6";
			} else {
				buffer = "6";
			}
			appendMark=true;
			append = true;
			showBuffer();
		}

	}

	class ButtonListner7 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if (append) {
				buffer = buffer + "7";
			} else {
				buffer = "7";
			}
			appendMark=true;
			append = true;
			showBuffer();
		}

	}

	class ButtonListner8 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if (append) {
				buffer = buffer + "8";
			} else {
				buffer = "8";
			}
			appendMark=true;
			append = true;
			showBuffer();
		}

	}

	class ButtonListner9 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if (append) {
				buffer = buffer + "9";
			} else {
				buffer = "9";
			}
			appendMark=true;
			append = true;
			showBuffer();
		}

	}

	class ButtonListner0 implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if (append) {
				buffer = buffer + "0";
			} else {
				buffer = "0";
			}
			appendMark=true;
			append = true;
			showBuffer();
		}

	}

	class ButtonListnerPlus implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if(appendMark){
				buffer = buffer + "+";
				appendMark = false;
			}else{
				//入力もないのに押されたら無視する
			}
			
			showBuffer();
		}

	}

	class ButtonListnerMinus implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if(appendMark){
				buffer = buffer + "-";
				appendMark = false;
			}else{
				//入力もないのに押されたら無視する
			}
			showBuffer();
		}

	}
	
	class ButtonListnerMul implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if(appendMark){
				buffer = buffer + "*";
				appendMark = false;
			}else{
				//入力もないのに押されたら無視する
			}
			showBuffer();
		}

	}
	
	class ButtonListnerDiv implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			
			if(appendMark){
				buffer = buffer + "/";
				appendMark = false;
			}else{
				//入力もないのに押されたら無視する
			}
			
			showBuffer();
		}
		

	}
	
	class ButtonListnerLA implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if(append){
				buffer = buffer + "(";
				appendArk += 1;
			}else{
				buffer= "(";
				appendArk+=1;
				append = true;
			}
			showBuffer();
		}

	}
	class ButtonListnerRA implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			if(appendArk>0){
				buffer = buffer + ")";
				appendArk -= 1;
			}else{
				//入力もないのに押されたら無視する
			}
			
			showBuffer();
		}

	}
	

	class ButtonListnerEqual implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			ExcuteNode node = new ExcuteNode(buffer);
			/*ArrayList<String> a = node.getCheckFormula();
			String str = new String("");
			
			for(Iterator i = a.iterator(); i.hasNext();){
				str += i.next();
			}
			mTextView.setText(str);*/
			mTextView.setText(String.valueOf(node.getResult()));
			
			//todo
			//計算して表示する
			
			//calculate();
		}

	}

	class ButtonListnerClear implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			initBuffer();
			initOperator();
			showBuffer();

		}

	}

	void showBuffer() {
		// 文字列として010などのように0が先頭についてしまう時，
		// これを一度Integerに変換すると10と認識される．
		// さらにこの10を文字列に変換しバッファにしまう．
		//buffer = Double.toString(Double.parseDouble(buffer));
		mTextView.setText(buffer);
	}

	void initBuffer() {
		buffer = null;
		buffer = new String("0");
		append=false;
		appendMark=false;
		appendArk = 0;
	}

	void initOperator() {
		operator = null;
		operator = new String("none");
	}

	void setOperator(String theOperator) {
		operator = theOperator;
	}

	/*void calculate() {
		if (operator.equals("plus")) {
			result = result + Integer.parseInt(buffer);
			buffer = Integer.toString(result);
			showBuffer();
		} else if (operator.equals("minus")) {
			result = result - Integer.parseInt(buffer);
			buffer = Integer.toString(result);
			showBuffer();
		} else {
			result = Integer.parseInt(buffer);
		}
	}*/
	

}
