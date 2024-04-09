<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Details</title>
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

h5 {
margin-block-start: 10px;
margin-block-end: 10px;
}
</style>
</head>
<body>
<div class="container">
    <div class="max-w-xl">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Request Method and URL</h5>
            </div>
            <div class="card-body">
                <pre><code class="hljs"><#if data.method??>${data.method}<#else>GET</#if>: <#if data.url??>${data.url}<#else>Unknown</#if></code></pre>
            </div>
        </div>

        <#if data.body??>
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Body</h5>
                </div>
                <div class="card-body">
                    <pre><code class="hljs">${data.body}</code></pre>
                </div>
            </div>
        </#if>

        <#if (data.headers)?has_content>
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Headers</h5>
                </div>
                <div class="card-body">
                    <#list data.headers as name, value>
                        <div>
                            <pre><code class="hljs"><b>${name}</b>: ${value}</code></pre>
                        </div>
                    </#list>
                </div>
            </div>
        </#if>

        <#if (data.cookies)?has_content>
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Cookies</h5>
                </div>
                <div class="card-body">
                    <#list data.cookies as name, value>
                        <div>
                            <pre><code class="hljs"><b>${name}</b>: ${value}</code></pre>
                        </div>
                    </#list>
                </div>
            </div>
        </#if>

        <#if data.curl??>
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Curl</h5>
                </div>
                <div class="card-body">
                    <pre><code class="hljs">${data.curl}</code></pre>
                </div>
            </div>
        </#if>
    </div>
</div>
</body>
</html>
