<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Books List</title>
                <style type="text/css">
                    body { font-family: Arial, sans-serif; background-color: #fafafa; }
                    h1 { color: #333; }
                    table { border-collapse: collapse; width: 95%; margin-top: 10px; }
                    th, td { border: 1px solid #888; padding: 6px 12px; text-align: left; }
                    th { background-color: #4CAF50; color: white; }
                    tr:nth-child(even) { background-color: #f2f2f2; }
                    a { text-decoration: none; color: #4CAF50; font-weight: bold; }
                </style>
            </head>
            <body>
                <h1>Books List</h1>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Year</th>
                        <th>Author ID</th>
                        <th>Author Name</th>
                        <th>Author BirthYear</th>
                    </tr>
                    <xsl:for-each select="books/book">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="title"/></td>
                            <td><xsl:value-of select="year"/></td>
                            <td><xsl:value-of select="author/id"/></td>
                            <td><xsl:value-of select="author/name"/></td>
                            <td><xsl:value-of select="author/birthYear"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
                <br/>
                <a href="/authors/xml">Go to Authors</a>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
