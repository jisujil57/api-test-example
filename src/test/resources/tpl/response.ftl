<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Response Details</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/3.0.9/tailwind.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.4.0/styles/atom-one-light.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.4.0/highlight.min.js"></script>
    <script>hljs.highlightAll();</script>
    <style>
        body {
background-color: #f3f3f3;
font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
padding: 20px;
}

.container {
display: flex;
flex-direction: column;
align-items: center;
justify-content: center;
}

.card {
position: relative; /* Добавляем относительное позиционирование */
border-radius: 8px;
box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
overflow: hidden;
margin-bottom: 20px;
max-width: 70%;
height: auto;
}

.card-header {
background-color: #2196F3;
color: #fff;
padding: 10px 15px;
border-top-left-radius: 8px;
border-top-right-radius: 8px;
height: auto;
}

.card-body {
padding: 10px 15px;
background-color: #fff;
height: auto;
}

pre code {
white-space: pre-wrap;
}

h4 {
margin-block-start: 10px;
margin-block-end: 10px;
}

.copy-icon {
position: absolute;
top: 5px;
right: 5px;
cursor: pointer;
font-size: 20px;
color: #555;
}
</style>
</head>
<body>
<div class="container">
    <div class="max-w-xl">
        <div class="card">
            <div class="card-header">
                <h4>Status code</h4>
            </div>
            <div class="card-body">
                <div class="copy-icon" onclick="copyToClipboard('response-code')">&#128203;</div>
                <pre><code class="hljs" id="response-code"><#if data.responseCode??>
                    ${data.responseCode}
                <#else>
                    Unknown
                </#if></code></pre>
            </div>
        </div>

        <#if data.url??>
            <div class="card">
                <div class="card-body">
                    <div class="copy-icon" onclick="copyToClipboard('url')">&#128203;</div>
                    <pre><code class="hljs" id="url">${data.url}</code></pre>
                </div>
            </div>
        </#if>

        <#if (data.headers)?has_content>
            <div class="card">
                <div class="card-header">
                    <h4>Headers</h4>
                </div>
                <div class="card-body">
                    <#list data.headers as name, value>
                        <div>
                            <div class="copy-icon" onclick="copyToClipboard('header-${name}')">&#128203;</div>
                            <pre><code class="hljs" id="header-${name}"><b>${name}</b>: ${value}</code></pre>
                        </div>
                    </#list>
                </div>
            </div>
        </#if>

        <#if data.body??>
            <div class="card">
                <div class="card-header">
                    <h4>Body</h4>
                </div>
                <div class="card-body">
                    <div class="copy-icon" onclick="copyToClipboard('body')">&#128203;</div>
                    <pre><code class="hljs" id="body">${data.body}</code></pre>
                </div>
            </div>
        </#if>

        <#if (data.cookies)?has_content>
            <div class="card">
                <div class="card-header">
                    <h4>Cookies</h4>
                </div>
                <div class="card-body">
                    <#list data.cookies as name, value>
                        <div>
                            <div class="copy-icon" onclick="copyToClipboard('cookie-${name}')">&#128203;</div>
                            <pre><code class="hljs" id="cookie-${name}"><b>${name}</b>: ${value}</code></pre>
                        </div>
                    </#list>
                </div>
            </div>
        </#if>
    </div>
</div>
<script>
    function copyToClipboard(id) {
var text = document.getElementById(id).innerText;
var textarea = document.createElement('textarea');
textarea.value = text;
document.body.appendChild(textarea);
textarea.select();
document.execCommand('copy');
document.body.removeChild(textarea);
alert('Copied to clipboard');
}
</script>
</body>
</html>
