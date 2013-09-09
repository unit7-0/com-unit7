#include <stdio.h>
#include <Windows.h>
#include "com_unit7_study_computergraphic_labs_test_IntroductionTest.h"
 
JNIEXPORT jint JNICALL Java_com_unit7_study_computergraphic_labs_test_IntroductionTest_getWidth
  (JNIEnv *, jclass) {
    HANDLE hWndConsole;
    if (hWndConsole = GetStdHandle(-12))
    {
        CONSOLE_SCREEN_BUFFER_INFO consoleInfo;
        if (GetConsoleScreenBufferInfo(hWndConsole, &consoleInfo))
        {
            int width = consoleInfo.srWindow.Right - consoleInfo.srWindow.Left + 1;
			return width;
        }
        else
            printf("Error: %d\n", GetLastError());
    }
    else
        printf("Error: %d\n", GetLastError());
    getchar();
    return EXIT_SUCCESS;
}

JNIEXPORT jint JNICALL Java_com_unit7_study_computergraphic_labs_test_IntroductionTest_getHeight
  (JNIEnv *, jclass) {
    HANDLE hWndConsole;
    if (hWndConsole = GetStdHandle(-12))
    {
        CONSOLE_SCREEN_BUFFER_INFO consoleInfo;
        if (GetConsoleScreenBufferInfo(hWndConsole, &consoleInfo))
        {
            int height = consoleInfo.srWindow.Bottom - consoleInfo.srWindow.Top + 1;
			return height;
        }
        else
            printf("Error: %d\n", GetLastError());
    }
    else
        printf("Error: %d\n", GetLastError());
    getchar();
    return EXIT_SUCCESS;
}

JNIEXPORT void JNICALL Java_com_unit7_study_computergraphic_labs_test_IntroductionTest_clearConsole
  (JNIEnv *, jclass) {
	system("cls");
}