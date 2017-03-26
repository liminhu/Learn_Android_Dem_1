#include "hookutils.h"
#include <sys/prctl.h>

int mybaseAdd1;

int (*old_AES_EnData)(void* r1,void* r2,void* r3, void * r4, void *r5, void *r6, void *r7) = NULL;
int my_AES_EnData(void* r1,void* r2,void* r3, void * r4, void *r5, void *r6, void *r7) {
    unsigned int lr;
    GETLR(lr);

    LogD("<%s>---lr:%x", __FUNCTION__,lr-mybaseAdd1);
    LogD("<%s>r1:%x,r2:%x, r3:%x,r4:%x,r5:%x,r6:%x,r7:%x", __FUNCTION__,r1,r2,r3,r4,r5,r6,r7);
    LogD("<%s>r1:%x[%s],r2:%x,r3:%x[%s]", __FUNCTION__,r1,r1,r2,r3,r3);
    LogD("<%s>r4:%x[%s],r5:%x[%s],r6:%x,r7:%x", __FUNCTION__,r4,r4,r5,r5,r6,r7);

    char *data1=stringToHexString((unsigned char *)r1, 48);
    char *data3=stringToHexString((unsigned char *)r3,20);

    char *data4=stringToHexString((unsigned char *)r4, 20);
    char *data5=stringToHexString((unsigned char *)r5,48);   //key- chameleon



    LogD("<%s>[r1-hex-48:%s],[r3-hex-20:%s]", __FUNCTION__,  data1,data3);
    LogD("<%s>[r4-hex-20:%s],[r5-hex-48:%s]", __FUNCTION__,  data4,data5);

    int ret = old_AES_EnData(r1,r2,r3,r4,r5,r6,r7);
    LogD("<%s>ret--r1:%x,r2:%x, r3:%x,r4:%x,r5:%x,r6:%x,r7:%x", __FUNCTION__,r1,r2,r3,r4,r5,r6,r7);

    data1=stringToHexString((unsigned char *)r1, 20);
    data3=stringToHexString((unsigned char *)r3,20);

    data4=stringToHexString((unsigned char *)r4, 20);
    data5=stringToHexString((unsigned char *)r5,20);

    LogD("<%s>ret-[r1-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data1,data3);
    LogD("<%s>ret-[r4-hex-20:%s],[r5-hex-20:%s]", __FUNCTION__,  data4,data5);
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}




int (*old_Sms_generate_54BA4)(void* r1,void* r2,void* r3) = NULL;
int my_Sms_generate_54BA4(void *r1, void *r2, void *r3) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s>---lr:%x", __FUNCTION__, lr - mybaseAdd1);

    LogD("<%s>r1:%x[%s],r2:%x[%s],r3:%x[%s]", __FUNCTION__,r1,r1,r2,r2,r3,r3);
   // LogD("<%s>r3:%x,r4:%x,r5:%x,r6:%x", __FUNCTION__,r3,r4,r5,r6);
    char *data1=stringToHexString((unsigned char *)r1, 20);
    char *data2=stringToHexString((unsigned char *)r2,20);
    char *data3=stringToHexString((unsigned char *)r3,20);
    LogD("<%s>[r1-hex-20:%s],[r2-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data1,data2,data3);
    int ret=old_Sms_generate_54BA4(r1,r2,r3);
    LogD("<%s>ret-r1:%x[%s],r2:%x[%s],r3:%x[%s]", __FUNCTION__,r1,r1,r2,r2,r3,r3);
    char *data4=stringToHexString((unsigned char *)r1, 20);
    char *data5=stringToHexString((unsigned char *)r2,20);
    char *data6=stringToHexString((unsigned char *)r3,20);
    LogD("<%s>ret-[r1-hex-20:%s],[r2-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data4,data5,data6);
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}





int (*old_Sms_genere_total_6993C)(void* r1) = NULL;
int my_Sms_genere_total_6993C(void *r1) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s>---lr:%x", __FUNCTION__, lr - mybaseAdd1);
    LogD("<%s>r1:%x[%s]", __FUNCTION__,r1,r1);
    char *data1=stringToHexString((unsigned char *)r1, 30);
    LogD("<%s>[r1-hex-30:%s]", __FUNCTION__,  data1);
    int ret=old_Sms_genere_total_6993C(r1);
    LogD("<%s>ret-r1:%x[%s]", __FUNCTION__,r1,r1);
    char *data4=stringToHexString((unsigned char *)r1, 30);
    LogD("<%s>ret-[r1-hex-30:%s]", __FUNCTION__,  data4);
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}






int (*old_Sms_hash_7C60C)(void* r1,void* r2,void* r3) = NULL;
int my_Sms_hash_7C60C(void *r1, void *r2, void *r3) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s>---lr:%x", __FUNCTION__, lr - mybaseAdd1);

    LogD("<%s>r1:%x[%s],r2:%x[%s],r3:%x[%s]", __FUNCTION__,r1,r1,r2,r2,r3,r3);
    // LogD("<%s>r3:%x,r4:%x,r5:%x,r6:%x", __FUNCTION__,r3,r4,r5,r6);
    char *data1=stringToHexString((unsigned char *)r1, 20);
    char *data2=stringToHexString((unsigned char *)r2,20);
    char *data3=stringToHexString((unsigned char *)r3,20);
    LogD("<%s>[r1-hex-20:%s],[r2-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data1,data2,data3);
    int ret=old_Sms_hash_7C60C(r1,r2,r3);
    LogD("<%s>ret-r1:%x[%s],r2:%x[%s],r3:%x[%s]", __FUNCTION__,r1,r1,r2,r2,r3,r3);
    char *data4=stringToHexString((unsigned char *)r1, 20);
    char *data5=stringToHexString((unsigned char *)r2,20);
    char *data6=stringToHexString((unsigned char *)r3,20);
    LogD("<%s>ret-[r1-hex-20:%s],[r2-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data4,data5,data6);
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}






int (*old_gen_net_hash_7C27C)(void* r1,void* r2,void* r3) = NULL;
int my_gen_net_hash_7C27C(void *r1, void *r2, void *r3) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s>---lr:%x", __FUNCTION__, lr - mybaseAdd1);

    LogD("<%s>r1:%x[%s],r2:%x[%s],r3:%x[%s]", __FUNCTION__,r1,r1,r2,r2,r3,r3);
    // LogD("<%s>r3:%x,r4:%x,r5:%x,r6:%x", __FUNCTION__,r3,r4,r5,r6);
    char *data1=stringToHexString((unsigned char *)r1, 20);
    char *data2=stringToHexString((unsigned char *)r2,20);
    char *data3=stringToHexString((unsigned char *)r3,20);
    LogD("<%s>[r1-hex-20:%s],[r2-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data1,data2,data3);
    int ret=old_gen_net_hash_7C27C(r1,r2,r3);
    LogD("<%s>ret-r1:%x[%s],r2:%x[%s],r3:%x[%s]", __FUNCTION__,r1,r1,r2,r2,r3,r3);
    char *data4=stringToHexString((unsigned char *)r1, 20);
    char *data5=stringToHexString((unsigned char *)r2,20);
    char *data6=stringToHexString((unsigned char *)r3,20);
    LogD("<%s>ret-[r1-hex-20:%s],[r2-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data4,data5,data6);
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}



//file_CDA74(char *a1, int a2, int a3, int a4)


void (*old_MgASdat_CDA74)(void* r1,void* r2,int r3, int r4) = NULL;
void my_MgASdat_CDA74(void *r1, void *r2, int r3,  int r4) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s>---lr:%x", __FUNCTION__, lr - mybaseAdd1);
   // LogD("<%s>r1:%x, r2:%x, r3:%x, r4:%x", __FUNCTION__,r1,r2,r3,r4);
   // LogD("<%s>r1:%x[%s],r2:%x,r3:%x[%s], r4:%x[%s]", __FUNCTION__,r1,r1,r2,r3,r3,r4,r4);
   // LogE("<%s> r3:%x[%s],[r3+4]:%x[%s]", __FUNCTION__,r3,r3,*(int *)(r3+4),*(int *)(r3+4));

    // LogD("<%s>r3:%x,r4:%x,r5:%x,r6:%x", __FUNCTION__,r3,r4,r5,r6);
  /*  char *data1=stringToHexString((unsigned char *)r1, 20);
    char *data2=stringToHexString((unsigned char *)r2,20);
    char *data3=stringToHexString((unsigned char *)r3,20);
    LogD("<%s>[r1-hex-20:%s],[r2-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data1,data2,data3);
    int ret=old_gen_net_hash_7C27C(r1,r2,r3);
    LogD("<%s>ret-r1:%x[%s],r2:%x[%s],r3:%x[%s]", __FUNCTION__,r1,r1,r2,r2,r3,r3);
    char *data4=stringToHexString((unsigned char *)r1, 20);
    char *data5=stringToHexString((unsigned char *)r2,20);
    char *data6=stringToHexString((unsigned char *)r3,20);
    LogD("<%s>ret-[r1-hex-20:%s],[r2-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data4,data5,data6);
    LogD("<%s>ret=%x ", __FUNCTION__, ret);*/
    old_MgASdat_CDA74(r1,r2,r3,r4);
    LogD("<%s> ret--lr:%x", __FUNCTION__,  lr - mybaseAdd1);
    LogD("<%s>  ret--r1:%x,r2:%x, r3:%x,r4:%x", __FUNCTION__, r1,r2,r3,r4);
    //LogD("<%s>  ret---r1:%x[%s],r2:%x,r3:%x[%s], r4:%x[%s]", __FUNCTION__,r1,r1,r2,r3,r3,r4,r4);
}







int (*old_File_EE09C)(void* r1,void* r2,void* r3, void * r4, void *r5, void *r6, void *r7,void *v8) = NULL;
int my_File_EE09C(void* r1,void* r2,void* r3, void * r4, void *r5, void *r6, void *r7,void *v8) {
    unsigned int lr;
    GETLR(lr);

    LogD("<%s>---lr:%x", __FUNCTION__,lr-mybaseAdd1);
    LogD("<%s>r1:%x,r2:%x, r3:%x,r4:%x,r5:%x,r6:%x,r7:%x, r8:%x", __FUNCTION__,r1,r2,r3,r4,r5,r6,r7, v8);

    int ret = old_File_EE09C(r1,r2,r3,r4,r5,r6,r7,v8);

    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}




int (*old_Cid_gen_13988)(void* r1,void *r2) = NULL;
int my_Cid_gen_13988(void *r1,void *r2) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s>---lr:%x", __FUNCTION__, lr - mybaseAdd1);
/*    LogD("<%s>r1:%x[%s]", __FUNCTION__,r1,r1);
    char *data1=stringToHexString((unsigned char *)r1, 30);
    LogD("<%s>[r1-hex-30:%s]", __FUNCTION__,  data1);*/
    int ret=old_Cid_gen_13988(r1,r2);
    /* LogD("<%s>ret-r1:%x[%s]", __FUNCTION__,r1,r1);
     char *data4=stringToHexString((unsigned char *)r1, 30);
     LogD("<%s>ret-[r1-hex-30:%s]", __FUNCTION__,  data4);*/
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}



int (*old_MD5_gen_2C400)(void* r1,void* r2,void* r3) = NULL;
int my_MD5_gen_2C400(void *r1, void *r2, void *r3) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s>---lr:%x", __FUNCTION__, lr - mybaseAdd1);

  //  LogD("<%s>r1:%x[%s],r2:%x[%s],r3:%x[%s]", __FUNCTION__,r1,r1,r2,r2,r3,r3);
    // LogD("<%s>r3:%x,r4:%x,r5:%x,r6:%x", __FUNCTION__,r3,r4,r5,r6);
/*    char *data1=stringToHexString((unsigned char *)r1, 20);
    char *data2=stringToHexString((unsigned char *)r2,20);
    char *data3=stringToHexString((unsigned char *)r3,20);*/
 //   LogD("<%s>[r1-hex-20:%s],[r2-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data1,data2,data3);
    int ret=old_MD5_gen_2C400(r1,r2,r3);
  /*  LogD("<%s>ret-r1:%x[%s],r2:%x[%s],r3:%x[%s]", __FUNCTION__,r1,r1,r2,r2,r3,r3);
    char *data4=stringToHexString((unsigned char *)r1, 20);
    char *data5=stringToHexString((unsigned char *)r2,20);
    char *data6=stringToHexString((unsigned char *)r3,20);
    LogD("<%s>ret-[r1-hex-20:%s],[r2-hex-20:%s],[r3-hex-20:%s]", __FUNCTION__,  data4,data5,data6);*/
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}






int (*old_cid1_gen_2C590)(void* r1,void *r2) = NULL;
int my_cid1_gen_2C590(void *r1,void *r2) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s>---lr:%x", __FUNCTION__, lr - mybaseAdd1);
/*    LogD("<%s>r1:%x[%s]", __FUNCTION__,r1,r1);
    char *data1=stringToHexString((unsigned char *)r1, 30);
    LogD("<%s>[r1-hex-30:%s]", __FUNCTION__,  data1);*/
    int ret=old_cid1_gen_2C590(r1,r2);
     LogD("<%s>ret-r2:%x", __FUNCTION__,r2);
     char *data4=stringToHexString((unsigned char *)r2, 16);
     LogD("<%s>ret-[r2-hex-16:%s]", __FUNCTION__,  data4);
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}






int (*old_MD52_gen_2B128)(void* r1,void *r2) = NULL;
int my_MD52_gen_2B128(void *r1,void *r2) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s>---lr:%x", __FUNCTION__, lr - mybaseAdd1);
  //  LogD("<%s>r1:%x[%s]", __FUNCTION__,r1,r1);
    char *data1=stringToHexString((unsigned char *)r2, 64);
    LogD("<%s>[r2-hex-64:%s]", __FUNCTION__,  data1);

    char *data2=stringToHexString((unsigned char *)r1, 24);
    LogD("<%s>[r1-hex-24:%s]", __FUNCTION__,  data2);

    int ret=old_MD52_gen_2B128(r1,r2);
    LogD("<%s>ret-r1:%x", __FUNCTION__,r1);
    char *data4=stringToHexString((unsigned char *)r1, 24);
    LogD("<%s>ret-[r2-hex-24:%s]", __FUNCTION__,  data4);
    LogD("<%s>ret=%x ", __FUNCTION__, ret);
    return ret;
}







int (*old_Base64_Encode_18810)(void* r1,void* r2,void* r3) = NULL;
int my_Base64_Encode_18810(void *r1, void *r2, void *r3) {
    unsigned int lr;
    GETLR(lr);
    LogD("<%s>---lr:%x--r2:%d", __FUNCTION__, lr - mybaseAdd1, r2);

    char *data1=stringToHexString((unsigned char *)r1, (int)r2);
    LogD("<%s>[r1-hex-%d:%s]", __FUNCTION__,  r2, data1);

    int ret=old_Base64_Encode_18810(r1,r2,r3);
    LogD("<%s>ret=%x --- ret:%s", __FUNCTION__, ret,ret);
    return ret;
}





//0xCFEAC
const static HOOK_Address gHookAddress[] = {

 /*       {0x18810,  (void *)&my_Base64_Encode_18810,(void **) &old_Base64_Encode_18810},

        {0x2B128,  (void *)&my_MD52_gen_2B128,(void **) &old_MD52_gen_2B128},

        {0x2C590, (void *)&my_cid1_gen_2C590,(void **) &old_cid1_gen_2C590},

        {0x2C400,  (void *)&my_MD5_gen_2C400,(void **) &old_MD5_gen_2C400},
        {0x13988,  (void *)&my_Cid_gen_13988,(void **) &old_Cid_gen_13988},*/

      // {0xCDA74-1,  (void *)&my_MgASdat_CDA74,(void **) &old_MgASdat_CDA74},

      //  {0xEE09C-1,  (void *)&my_File_EE09C,(void **) &old_File_EE09C},
        //第三四个参数是sub_CFEAC的输出值, 第5， 6个参数用hex str输出
      //S {0xCFEAC-1,  (void *)&my_AES_EnData,(void **) &old_AES_EnData},


        //sms--相关的
       // {0x54BA4,  (void *)&my_Sms_generate_54BA4,(void **) &old_Sms_generate_54BA4},
      //  {0x6993C,  (void *)&my_Sms_genere_total_6993C,(void **) &old_Sms_genere_total_6993C},
      //  {0x7C60C,  (void *)&my_Sms_hash_7C60C,(void **) &old_Sms_hash_7C60C},


     //   {0x7C27C,  (void *)&my_gen_net_hash_7C27C,(void **) &old_gen_net_hash_7C27C},

};

const static FIND_ADDRESS gFindAddress[] = {

};


void hook_address(int baseAddr) {

#ifdef NDK_DEBUG
    LogD("<%s> %s %s  baseAddr = 0x%x ", __FUNCTION__, __DATE__,__TIME__, baseAddr);
#endif

    prctl(PR_SET_DUMPABLE, 1, NULL, NULL, NULL);
    for (unsigned int i = 0; i < sizeof(gFindAddress) / sizeof(gFindAddress[0]); i++) {
        FIND_ADDRESS hook = gFindAddress[i];
        *(hook.func) = (void *) (baseAddr + hook.address);
#ifdef NDK_DEBUG
        LogD("<%s> address 0x%x found at 0x%x", __FUNCTION__, (int)hook.address,(int) *(hook.func));
#endif
    }

    for (unsigned int i = 0; i < sizeof(gHookAddress) / sizeof(gHookAddress[0]); i++) {
        HOOK_Address hook = gHookAddress[i];

        if (*hook.old_func != NULL) {
#ifdef NDK_DEBUG
            LogD("<%s> address %x already hooked %x", __FUNCTION__, hook.address, (int)*(hook.old_func));
#endif
            continue;
        }
        inlineHookAddress((void *) (baseAddr + hook.address), hook.new_func, hook.old_func);

        LogD("my_<%s> address %x hooked to new 0x%x, (old 0x%x)", __FUNCTION__, (int)hook.address,(int) hook.new_func, (int)*hook.old_func);

    }
}
