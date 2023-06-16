def dict2xml(dic):
    content = ""
    for item in dic.items():
        content += f"""
    <property>
        <name>{item[0]}</name>
        <value>{item[1]}</value>
    </property>
        """
        pass
    return f"""<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
    {content}
</configuration>"""

