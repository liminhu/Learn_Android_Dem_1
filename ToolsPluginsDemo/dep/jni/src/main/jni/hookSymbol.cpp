#include "hookutils.h"

int mybaseAdd;
//-----------------------------------------

int (*old_tKl0u_98_fdmm)(void* r1,void* r2,void* r3) = NULL;
int my_tKl0u_98_fdmm_pKl0u98fdmd(void *r1, void *r2, void *r3) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s> r1:%s,r2:%s,r3:%d--lr:%x", __FUNCTION__, r1,r2,r3,lr-mybaseAdd);
    int ret = old_tKl0u_98_fdmm(r1,r2,r3);
    LogD("<%s>ret=%x", __FUNCTION__, ret);
    return ret;
}







int (*old_FileDecode)(void* r1,void* r2,void* r3, void * r4) = NULL;
int my_FileDecode(void *r1, void *r2, void *r3, void * r4) {
    unsigned int lr;
    GETLR(lr);
   // char *data=stringToHexString((unsigned char *)r1,9);
    LogD("<%s>r1:%x, r2:%x, lr:%x", __FUNCTION__,  r1, r2, lr-mybaseAdd);
    int ret = old_FileDecode(r1,r2,r3,r4);
   // LogD("<%s> r3:%d--r4:%d", __FUNCTION__,  lr-mybaseAdd, r3,r4);
  //  LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}



int (*old_Z11det5e9u_03bPhPci)(void* r1,void* r2,void* r3) = NULL;
int my_Z11det5e9u_03bPhPci_psdet5e9u03b(void *r1, void *r2, void *r3) {
    unsigned int lr;
    GETLR(lr);
    char *data=stringToHexString((unsigned char *)r1,9);
    LogD("<%s> lr:%x--data:%s", __FUNCTION__,  lr-mybaseAdd, data);
    int ret = old_Z11det5e9u_03bPhPci(r1,r2,r3);
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}




void doProcessCheat(int flag, int arg1, int arg2) {
    //LogD("my1_<%s> flag=%d,arg1=%d,arg2=%d player=%x", __FUNCTION__, flag, arg1, arg2, player);

}








int (*old_CEncrypt_decrypt)(void* r1,void* r2,void* r3) = NULL;
int my_CEncrypt_decrypt(void *r1, void *r2, void *r3) {
    unsigned int lr;
    GETLR(lr);
    char *data=stringToHexString((unsigned char *)r2,9);
    LogD("<%s> lr:%x--data:%s", __FUNCTION__,  lr-mybaseAdd, data);
    LogD("<%s> r1:%x--r3:%s", __FUNCTION__, r1,r3);
    int ret = old_CEncrypt_decrypt(r1,r2,r3);
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}







const static HOOK_SYMBOL gHookSymbols[] = {

    //    {"_Z10FileDecodePKcPcS1_Pi", (void *)&my_FileDecode,(void **) &old_FileDecode},

};
const static FIND_SYMBOL gFindSymbols[] = {
        //新增
       // {"0x35F790",(void **) &old_data},          //是否是自控制

};

void hook_symbols(soinfo *handle) {

#ifdef NDK_DEBUG
    LogD("<%s> %s %s handle = %x( funcs = %d)", __FUNCTION__, __DATE__,__TIME__,(int)handle, sizeof(gHookSymbols)/sizeof(gHookSymbols[0]));
#endif

    for (int i = 0; i < sizeof(gFindSymbols) / sizeof(gFindSymbols[0]); i++) {
        FIND_SYMBOL find = gFindSymbols[i];
        *find.func = dlsym(handle, find.symbol);
      //  LogD("<HookSymbol> symbol = 0x%x : %s", (int)*find.func, find.symbol);
    }

    for (int i = 0; i < sizeof(gHookSymbols) / sizeof(gHookSymbols[0]); i++) {
        HOOK_SYMBOL hook = gHookSymbols[i];
        inlineHookSymbol(handle, hook.symbol, hook.new_func, hook.old_func);

       // LogD("<HookSymbol> symbol = %s(%x), new = 0x%x, old = 0x%x", hook.symbol, (int)getAddress(handle,hook.symbol ),(int)hook.new_func, (int)*hook.old_func);
#ifdef NDK_DEBUG
      //  LogD("<HookSymbol> symbol = %s(%x), new = 0x%x, old = 0x%x", hook.symbol, (int)getAddress(handle,hook.symbol ),(int)hook.new_func, (int)*hook.old_func);
#endif
    }

}
