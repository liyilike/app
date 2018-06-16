# -*- coding: utf-8 -*-
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

sys.stdout = io.TextIOWrapper(sys.stdout.buffer,encoding='utf-8') #改变标

appid = '20151113000005349'
secretKey = 'osubCEzlGjzvw8qdQc41'
fromLang = 'auto'
salt = 654
languageArr =['zh','en','jp','cht','kor','fra','spa','th' ,'ara','ru','pt','de','it','el','nl','pl','bul','est','dan' ,'fin' ,'cs','rom','slo','swe','hu','vie']
# languageArr =['zh','en','jp','cht','kor']
# languageArr =['kor']
translate =''
text =''
result_object = ""
all_the_text = ""
# 创建res文件夹
# filemMkdir(os.getcwd() +r"\res")
# all_the_text = fileRead('strings.xml')
# dataConArr = fileReadLine('strings.xml')

# for conArr in dataConArr:
#     pattern = re.compile('<string([\s\S]*?)>([\s\S]*?)</string>')
#     dataArr = pattern.findall(conArr)
#     if len(dataArr)>=1:
#        translate = translate +'\n'+ dataArr[0][1]
for language in languageArr:
    if language == 'en': 
       text = '英语'
    if language == 'zh': 
       text = '中文'
    if language == 'jp': 
       text = '日文'
    if language == 'cht': 
       text = '中文'
    if language == 'kor': 
       text = '韩文'
    if language == 'fra': 
       text = '法语'
    if language == 'spa': 
       text = '西班牙语'
    if language == 'th': 
       text = '泰语'
    if language == 'ara': 
       text = '阿拉伯语'
    if language == 'ru': 
       text = '俄语'
    if language == 'pt': 
       text = '葡萄牙语'
    if language == 'de': 
       text = '德语'
    if language == 'it': 
       text = '意大利语'
    if language == 'el': 
       text = '希腊语'
    if language == 'nl': 
       text = '荷兰语'
    if language == 'pl': 
       text = '波兰语'
    if language == 'bul': 
       text = '保加利亚语'
    if language == 'est': 
       text = '爱沙尼亚语'
    if language == 'dan': 
       text = '丹麦语'
    if language == 'fin': 
       text = '芬兰语'
    if language == 'cs': 
       text = '捷克语'
    if language == 'rom': 
       text = '罗马尼亚语'
    if language == 'slo': 
       text = '斯洛文尼亚语'
    if language == 'swe': 
       text = '瑞典语'
    if language == 'hu': 
       text = '匈牙利语'
    if language == 'vie': 
       text = '越南语'
    translate = '本软件支持' + text
    q = translate
    toLang = language
    # salt = random.randint(32768, 65536) 
    sign = appid+q+str(salt)+secretKey
    m = hashlib.md5(sign.encode(encoding='utf-8')).hexdigest()  
    myurl = 'http://api.fanyi.baidu.com/api/trans/vip/translate?appid='+appid+'&q='+urllib.parse.quote(q)+'&from='+fromLang+'&to='+toLang+'&salt='+str(salt)+'&sign='+m
    data = urllib.request.urlopen(urllib.request.Request(myurl)).read().decode('utf-8')
    # print (data)
    jsonData = json.loads(data)
    result_object =result_object+ jsonData['trans_result'][0]['dst']+'\n' 
    # print (result_object)
    # result_object = result +'/n'
    time.sleep(8)
xmlpath = "support.txt"
fileWrite(xmlpath,result_object)
    # 
    # jsonDataArr =jsonData['trans_result'] 
    # # 获取的翻译数据循环替换生成数据
    # for jsonStr in jsonDataArr:
    #     result_object =result_object.replace(jsonStr['src'],jsonStr['dst'])
    # #如果是英文就做多一步替换values的string.xml
    # if language == "en": 
    #    path = os.getcwd() +r"\res\values"
    #    xmlpath = path+"\strings.xml"
    #    filemMkdir(path)
    #    fileWrite(xmlpath,result_object)
    # if language == "zh": 
    #    path = os.getcwd() +r"\res\values-zh-rCN"
    #    xmlpath = path+"\strings.xml"
    #    filemMkdir(path)
    #    fileWrite(xmlpath,result_object)
    # if language == "cht": 
    #    path = os.getcwd() +r"\res\values-zh-rTW"
    #    xmlpath = path+"\strings.xml"
    #    filemMkdir(path)
    #    fileWrite(xmlpath,result_object)
    #    path = os.getcwd() +r"\res\values-zh-rHK"
    #    xmlpath = path+"\strings.xml"
    #    filemMkdir(path)
    #    fileWrite(xmlpath,result_object)
    #    continue
    # path = os.getcwd() +r"\res\values-"+language
    # if language == "rom": 
    #    path = os.getcwd() +r"\res\values-ro" 
    # if language == "slo": 
    #    path = os.getcwd() +r"\res\values-sl" 
    # if language == "jp": 
    #    path = os.getcwd() +r"\res\values-ja" 
    # xmlpath = path+"\strings.xml"
    # filemMkdir(path)
    # fileWrite(xmlpath,result_object)
    # print (language)
