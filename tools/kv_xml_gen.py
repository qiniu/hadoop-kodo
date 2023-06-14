# 命令行参数，第一个参数为输出文件名，后续n个参数分别为key, value,n为2的整数倍
import sys
from common import dict2xml

if __name__ == "__main__":
    if len(sys.argv) < 2 or len(sys.argv) % 2 != 0:
        print("Usage: gen_xml.py <output file> <key1> <value1> <key2> <value2> ...")
        sys.exit(1)

    dic = {}
    for i in range(2, len(sys.argv), 2):
        dic[sys.argv[i]] = sys.argv[i+1]

    xml = dict2xml(dic)

    with open(sys.argv[1], 'w') as f:
        f.write(xml)
