

#include "hookutils.h"

/*#define VICTIM_LIB "libdexmmb.so"*/

#define VICTIM_LIB "libmgRun_04.21.03_01.so"

extern  int mybaseAdd;
extern  int mybaseAdd1;

extern void doProcessCheat(int flag, int arg1, int arg2);
JNIEXPORT void JNICALL jniDoProcessCheat(JNIEnv *env, jclass clazz, jint flag, jint arg1, jint arg2) {
	doProcessCheat(flag, arg1, arg2);
}

dlopen_callback gCallback;

static const char *LoadingLibrary(const char *filename){
	return filename;
}



FILE * my_fopen(const char* path, const char * mode) {
	unsigned lr;
	GETLR(lr);
//	LogD("<%s> my_fopen_path:%s, lr:%x", __FUNCTION__, path, lr-mybaseAdd);

	//于判断字符串str2是否是str1的子串
	if(strstr(path,"/proc/")!=NULL){
		if(strstr(path,"/task/")!=NULL||strstr(path,"/status")!=NULL) {
		    if(strstr(path,"/status")!=NULL || strstr(path,"/proc/self/task/")!=NULL){
				LogD("111<%s> anti_debug,old path=%s, lr:%x", __FUNCTION__, path,lr-mybaseAdd);
				char *replacePath=(char*)malloc(82);
				memset(replacePath,0,82);
				strcpy(replacePath,"/data/local/tmp/new_anti_antidebug");
				path=replacePath;
			//	LogD("111<%s> anti_debug,new path=%s, lr:%x", __FUNCTION__, path,lr-mybaseAdd);
				FILE * f1 = fopen(path, mode);
				free(replacePath);
				//LogD("111<%s> ret:%x, lr:%x", __FUNCTION__, f1, lr-mybaseAdd);
				return f1;
		    }
		}
	}else{
		LogE("<%s> my_fopen_path:%s, lr:%x", __FUNCTION__, path, lr-mybaseAdd);
	}
	FILE * f = fopen(path, mode);
//	LogD("<%s> ret:%x, lr:%x", __FUNCTION__, f, lr-mybaseAdd);
	return f;
}



int my_getppid(){
	unsigned lr;
	GETLR(lr);
	//LogD("<%s> lr:%x", __FUNCTION__,  lr-mybaseAdd);
	int f = getppid();
	//pid=f;
	//LogD("<%s> ret(ppid):%d, lr:%x", __FUNCTION__, f, lr-mybaseAdd);
	return f;
}



static int ppid=0;
int my_atoi(const char * str){
	unsigned lr;
	GETLR(lr);
	int data=lr-mybaseAdd;
	//LogD("<%s> str:%s, lr:%x", __FUNCTION__, str, lr-mybaseAdd);
	int f = atoi(str);
	if(data==0xae553){
	//	LogD("<%s> pid--[%x]-->ppid, lr:%x", __FUNCTION__, f, lr-mybaseAdd);
		return my_getppid();
	}else if(data==0xae7e9 || data==0xae687){
		//LogD("<%s> pid--[%x]-->ppid, lr:%x", __FUNCTION__, f, lr-mybaseAdd);
		return 0;
	}
	//LogD("<%s> ret:%d---pid:%d, lr:%x", __FUNCTION__, f,  ppid, lr-mybaseAdd);
	//f=pid;
	return f;
}




int my_getpid(){
	unsigned lr;
	GETLR(lr);
//	LogD("pid<%s> lr:%x", __FUNCTION__,  lr-mybaseAdd);
	int f = getpid();
	//pid=f;
//	LogD("pid<%s> ret(pid):%d, lr:%x", __FUNCTION__, f, lr-mybaseAdd);
	return f;
}



int my_strncmp(char *s1, char *s2, int n) {
	unsigned lr;
	GETLR(lr);
	LogD("<%s> s1=[%x]%s,  s2=[%x]%s,  lr:%x", __FUNCTION__, s1,s1,s2,s2, lr-mybaseAdd);
	char *data1=stringToHexString((unsigned char *)s1,n);
	char *data2=stringToHexString((unsigned char *)s2,n);
	LogD("<%s>data1=%s,  data2:%s", __FUNCTION__, data1,data2);
	LogD("<%s> n:%d, lr:%x", __FUNCTION__,  n, lr-mybaseAdd);
	return strncmp(s1,s2,n);
}





int my_strlen(const char *str) {
	unsigned lr;
	GETLR(lr);
	LogD("<%s> str:%s, lr:%x", __FUNCTION__, str, lr-mybaseAdd);
	return strlen(str);
}



int my_gettimeofday(struct timeval*tv, struct timezone *tz) {
	unsigned lr;
	GETLR(lr);
	static long sec = 0;
	static long usec = 0;
	if (sec == 0) {
		int ret = gettimeofday(tv, tz);
		sec = tv->tv_sec;
		usec = tv->tv_usec;
		LogD("<%s> ret-sec:%d, lr:%x", __FUNCTION__, sec, lr-mybaseAdd);
		return ret;
	} else {
		tv->tv_sec = sec;
		tv->tv_usec = usec;
		LogD("<%s> 0-sec:%d, lr:%x", __FUNCTION__, sec, lr-mybaseAdd);
		return 0;
	}
}



void *my_memcpy(void *dest, const void *src, size_t n) {
	unsigned lr;
	GETLR(lr);
	LogD("<%s> src[0x%x]:%s -- size:%d, lr:%x", __FUNCTION__, src,src, n, lr-mybaseAdd);
	char *data1=stringToHexString((unsigned char *)src, n);
	LogD("<%s> %d-data1:%s, lr:%x", __FUNCTION__, n,data1, lr-mybaseAdd);
	return memcpy(dest, src, n);
}

void my_free(void *ptr) {
	unsigned lr;
	GETLR(lr);
	LogD("<%s> r1:[0x%x]:%s -- lr:%x", __FUNCTION__, ptr,ptr, lr-mybaseAdd);
	return free(ptr);
}


static void LibraryLoaded(const char *filename, void *handle){
	//LogE("my_<%s> LibraryLoaded:%s", __FUNCTION__, filename);
	if(strstr(filename, VICTIM_LIB) && handle){
		unregistDlopen(gCallback);
		soinfo *info = (soinfo*)handle;
		hook_address(info->base);
		hook_symbols(info);
		mybaseAdd=info->base;
		mybaseAdd1=info->base;
		LogD("my_<%s>  is load ... baseadd:%x", __FUNCTION__ ,mybaseAdd);
		pltHook(LIBC, "fopen", VICTIM_LIB, (int) my_fopen);

	/*	pltHook(LIBC, "atoi", VICTIM_LIB, (int) my_atoi);  //与下两个一起
		pltHook(LIBC, "getppid", VICTIM_LIB, (int) my_getppid);
		pltHook(LIBC, "getpid", VICTIM_LIB, (int) my_getpid);*/

		//pltHook(LIBC, "gettimeofday", VICTIM_LIB, (int) my_gettimeofday);
/*		pltHook(LIBC, "strlen", VICTIM_LIB, (int) my_strlen);
		pltHook(LIBC, "strncmp", VICTIM_LIB, (int) my_strncmp);

		pltHook(LIBC, "memcpy", VICTIM_LIB, (int) my_memcpy);*/
	//	pltHook(LIBC, "free", VICTIM_LIB, (int) my_free);     // 不能轻易打开
	}
	return;
}

void hookPrepare(){
	void *handle = findLoadedLib(VICTIM_LIB);
	if(handle){
		soinfo *info = (soinfo*)handle;
		hook_address(info->base);
		hook_symbols(info);
	}else{
		gCallback.onLoadingLibrary = LoadingLibrary;
		gCallback.onLibraryLoaded = LibraryLoaded;
		registDlopen(gCallback);
	}
}

static const JNINativeMethod gHookMethods[] = {
	{ "nativeDoCheat", "(III)V", (void*)jniDoProcessCheat },
};

extern "C" __attribute__ ((visibility("default"))) jint JNI_OnLoad(JavaVM* vm, void* reserved){

	#ifdef NDK_DEBUG
	LogD("<%s> libA8 %s %s", __FUNCTION__, __DATE__, __TIME__);
	#endif

	JNIEnv* env = NULL;

	if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
		return -1;
	}

	jclass clazz;

	clazz = env->FindClass("com/gameassist/plugin/nativeutils/NativeUtils");
	if (clazz == NULL) {
		return -1;
	}

	if (env->RegisterNatives(clazz, gHookMethods, sizeof(gHookMethods) / sizeof(gHookMethods[0])) < 0) {
		return -1;
	}
	LogD("my_<%s>  is load ... 0000", __FUNCTION__);
	hookPrepare();
	return JNI_VERSION_1_4;
}
