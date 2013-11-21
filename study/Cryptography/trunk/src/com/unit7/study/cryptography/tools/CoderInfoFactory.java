package com.unit7.study.cryptography.tools;

import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab3.Algorithm;

public interface CoderInfoFactory {
	CoderInfo createCoderInfo(Algorithm type, Object... args);
}
