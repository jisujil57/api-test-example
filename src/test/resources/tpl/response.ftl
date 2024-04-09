<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Response Details</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.7.2/styles/github.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.7.2/highlight.min.js"></script>
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
border-radius: 8px;
box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
overflow: hidden;
margin-bottom: 20px;
height: auto;
margin-left: 20%;
margin-right: 20%;
}

.card-header {
background: linear-gradient(163deg, #1f8bdf 0%, #209fdb 100%);
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

pre code.hljs {
padding: 16px;
}

pre code {
white-space: pre-wrap;
padding: 16px;
}

pre {
white-space: pre-wrap;
margin: 6px 0px;
}

h4 {
margin-block-start: 2px;
margin-block-end: 2px;
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
                <pre><code class="hljs ftl" id="response-code"><#if data.responseCode??>${data.responseCode}
                <#else>
                    Unknown
                </#if></code></pre>
            </div>
        </div>

        <!-- Карточка для URL -->
        <#if data.url??>
            <div class="card">
                <div class="card-body">
                    <pre><code class="hljs" id="url">${data.url}</code></pre>
                </div>
            </div>
        </#if>

        <!-- Карточка для Headers -->
        <#if (data.headers)?has_content>
            <div class="card">
                <div class="card-header">
                    <h4>Headers</h4>
                </div>
                <div class="card-body">
                    <#list data.headers as name, value>
                        <div>
                            <pre><code class="hljs" id="header-${name}"><b>${name}</b>: ${value}</code></pre>
                        </div>
                    </#list>
                </div>
            </div>
        </#if>

        <!-- Карточка для Body -->
        <#if data.body??>
            <div class="card">
                <div class="card-header">
                    <h4>Body</h4>
                </div>
                <div class="card-body">
                    <pre><code class="hljs" id="body">${data.body}</code></pre>
                </div>
            </div>
        </#if>

        <!-- Карточка для Cookies -->
        <#if (data.cookies)?has_content>
            <div class="card">
                <div class="card-header">
                    <h4>Cookies</h4>
                </div>
                <div class="card-body">
                    <#list data.cookies as name, value>
                        <div>
                            <pre><code class="hljs" id="cookie-${name}"><b>${name}</b>: ${value}</code></pre>
                        </div>
                    </#list>
                </div>
            </div>
        </#if>
    </div>
</div>
</body>
</html>
