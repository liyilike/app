#coding=utf-8 
import json
import urllib
import urllib.request
import re
import hashlib
import urllib
import random
import time
import os
import sys
import io
import codecs

# 可写函数说明
def fileWrite(xmlpath,result_object):
    file=codecs.open(xmlpath,"w","utf-8")
    file.write(result_object)
    file.close()

# 可写函数说明
def filemMkdir(path):
    isExists=os.path.exists(path)
    if not isExists: #判断是否有这个目录,没有就创建
       os.mkdir(path)

# 可写函数说明
def fileRead(path):
    file_object = open(path,'r',encoding= 'utf-8')
    try:
        all_the_text = file_object.read()
    finally:
        file_object.close()
    return all_the_text

# 可写函数说明
def fileReadLine(path):
    file_object = open(path,'r',encoding= 'utf-8')
    dataConArr = file_object.readlines()
    file_object.close()
    return dataConArr

# 可写函数说明
def removeList(list):
    resultList=[]
    for data in list:
        pattern = re.compile('<string([\s\S]*?)>([\s\S]*?)</string>')
        if pattern.findall(data):
           resultList.append(data)    
    # print (len(resultList))  
    return resultList

#sys.stdout = io.TextIOWrapper(sys.stdout.buffer,encoding='utf-8') #改变标

appid = '20151113000005349'
secretKey = 'osubCEzlGjzvw8qdQc41'
fromLang = 'zh'
salt = 654
languageArr =['zh','en','jp','kor','fra','spa','th' ,'ara','ru','pt','de','it','el','nl','pl','bul','est','dan' ,'fin' ,'cs','rom','slo','swe','hu','cht','vie']
#strings.xml获取的数列中文字眼string里面那些字符列表
#languageArr =['kor']

translate =''
result_object = ""
all_the_text = ""
# 创建res文件夹
filemMkdir(os.getcwd() +r"\res")
all_the_text = fileRead('strings.xml')
dataConArr = fileReadLine('strings.xml')
dataConArr = removeList(dataConArr)

for conArr in dataConArr:
    pattern = re.compile('<string([\s\S]*?)>([\s\S]*?)</string>')
    dataArr = pattern.findall(conArr)
    if len(dataArr)>=1:
       translate = translate+'\n'+ dataArr[0][1]

for language in languageArr:
    if language == "zh": 
       path = os.getcwd() +r"\res\values-zh-rCN"
       xmlpath = path+"\strings.xml"
       filemMkdir(path)
       fileWrite(xmlpath,all_the_text)
       continue
    result_object = ''
    q = translate
    toLang = language
    # salt = random.randint(32768, 65536) 
    sign = appid+q+str(salt)+secretKey
    m = hashlib.md5(sign.encode(encoding='utf-8')).hexdigest()  
    myurl = 'http://api.fanyi.baidu.com/api/trans/vip/translate?appid='+appid+'&q='+urllib.parse.quote(q)+'&from='+fromLang+'&to='+toLang+'&salt='+str(salt)+'&sign='+m
    data = urllib.request.urlopen(urllib.request.Request(myurl)).read().decode('utf-8')
    # print (data)
    jsonData = json.loads(data)
    # print (data)
    jsonDataArr =jsonData['trans_result'] 
    
    # 获取的翻译数据循环替换生成数据
    for i in range(len(dataConArr)):
        jsonStr = jsonDataArr[i]
        dataStr = dataConArr[i]
        dataStr = dataStr.replace(jsonStr['src'],jsonStr['dst'])
        result_object = result_object + dataStr 
    result_object ="<resources>\n"+result_object+"</resources>"
    # for jsonStr in jsonDataArr:
    #     result_object =result_object.replace(jsonStr['src'],jsonStr['dst'])
    #如果是英文就做多一步替换values的string.xml
    if language == "en": 
       path = os.getcwd() +r"\res\values"
       xmlpath = path+"\strings.xml"
       filemMkdir(path)
       fileWrite(xmlpath,result_object)
    if language == "cht": 
       path = os.getcwd() +r"\res\values-zh-rTW"
       xmlpath = path+"\strings.xml"
       filemMkdir(path)
       fileWrite(xmlpath,result_object)
       path = os.getcwd() +r"\res\values-zh-rHK"
       xmlpath = path+"\strings.xml"
       filemMkdir(path)
       fileWrite(xmlpath,result_object)
       path = os.getcwd() +r"\res\values-zh-rMO"
       xmlpath = path+"\strings.xml"
       filemMkdir(path)
       fileWrite(xmlpath,result_object)
       # path = os.getcwd() +r"\res\values-zh-rMY"
       # xmlpath = path+"\strings.xml"
       # filemMkdir(path)
       # fileWrite(xmlpath,result_object)
       # path = os.getcwd() +r"\res\values-zh-rSG"
       # xmlpath = path+"\strings.xml"
       # filemMkdir(path)
       # fileWrite(xmlpath,result_object)
       continue
    path = os.getcwd() +r"\res\values-"+language
    if language == "rom": 
       path = os.getcwd() +r"\res\values-ro" 
    if language == "slo": 
       path = os.getcwd() +r"\res\values-sl" 
    if language == "jp": 
       path = os.getcwd() +r"\res\values-ja" 
    xmlpath = path+"\strings.xml"
    filemMkdir(path)
    print(result_object)
    fileWrite(xmlpath,result_object)
    print (language)
    time.sleep(8)
