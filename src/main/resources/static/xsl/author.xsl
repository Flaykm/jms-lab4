<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Authors List</title>
                <style type="text/css">
                    body { font-family: Arial, sans-serif; background-color: #fafafa; }
                    h1 { color: #333; }
                    table { border-collapse: collapse; width: 60%; margin-top: 10px; }
                    th, td { border: 1px solid #888; padding: 6px 12px; text-align: left; }
                    th { background-color: #4CAF50; color: white; }
                    tr:nth-child(even) { background-color: #f2f2f2; }
                    a { text-decoration: none; color: #4CAF50; font-weight: bold; }
                </style>
            </head>
            <body>
                <h1>Authors List</h1>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Birth Year</th>
                    </tr>
                    <xsl:for-each select="authors/author">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="birthYear"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
                <br/>
                <a href="/books/xml">Go to Books</a>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
