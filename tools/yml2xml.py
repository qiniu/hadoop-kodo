import yaml

import sys
import xml.etree.ElementTree as ET
from common import dict2xml


def flat_map(src, target=None, prefix=""):
    if target is None:
        target = {}
    for k, v in src.items():
        if type(v) is dict:
            flat_map(v, target, prefix + k + ".")
        else:
            target[prefix + k] = v
    return target


if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: yml2xml.py <yml file> <xml file>")
        sys.exit(1)

    with open(sys.argv[1], 'r') as f:
        yml = yaml.load(f, Loader=yaml.FullLoader)

    flatten_dict = flat_map(yml)
    xml = dict2xml(flatten_dict)

    with open(sys.argv[2], 'w') as f:
        f.write(xml)
