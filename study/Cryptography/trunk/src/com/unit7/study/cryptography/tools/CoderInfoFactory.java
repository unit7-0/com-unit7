package com.unit7.study.cryptography.tools;

import com.unit7.study.cryptography.labs.lab2.CoderInfo;

public interface CoderInfoFactory {
	CoderInfo createCoderInfo(String type, Object... args);
}
