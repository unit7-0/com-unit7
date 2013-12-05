package com.unit7.study.cryptography.labs.lab6;

import com.unit7.study.cryptography.labs.lab6.interfaces.Question;
import com.unit7.study.cryptography.labs.lab6.interfaces.QuestionType;

public class QuestionImpl implements Question {
	@Override
	public QuestionType getType() {
		return type;
	}

	@Override
	public void setType(QuestionType type) {
		this.type = type;
	}

	private QuestionType type;
}
