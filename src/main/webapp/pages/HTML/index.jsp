<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <jsp:useBean id="controller" class="com.example.oppsem2labjavae1.Beans.Controller" scope="request"/>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home</title>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/woocommerce-layout.css' type='text/css' media='all'/>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/woocommerce-smallscreen.css' type='text/css' media='only screen and (max-width: 768px)'/>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/woocommerce.css' type='text/css' media='all'/>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/font-awesome.min.css' type='text/css' media='all'/>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/style.css' type='text/css' media='all'/>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/easy-responsive-shortcodes.css' type='text/css' media='all'/>
    <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Oswald:400,500,700%7CRoboto:400,500,700%7CHerr+Von+Muellerhoff:400,500,700%7CQuattrocento+Sans:400,500,700' type='text/css' media='all'/>
</head>
<body class="blog">
<div id="page">
    <div class="container">
        <header id="masthead" class="site-header">
            <div class="site-branding">
                <h1 class="site-title">Library</h1>

                <%
                    if (request.getParameterMap().containsKey("logout")) {
                        controller.logout(request);
                        response.sendRedirect("/library/index");
                    }
                %>

                <button name="logoutBtn" onclick="location.href = '${pageContext.request.contextPath}/index?logout'">Logout</button>
            </div>
            <nav id="site-navigation" class="main-navigation">
                <ul id="menu-menu-1" class="menu">
                    <%
                        if (controller.hasRole(request, "librarian")) {
                            out.println("<li><a href=\"/library/book-management\">Book Management</a></li>");
                        }
                    %>
                </ul>
            </nav>
        </header>
        <input id="prompt-input-field" oninput="search()" style="width: 100%; text-align: center" placeholder="Search prompt">

        <div id="content" class="site-content">
            <div id="primary" class="content-area column half">
                <main id="main" class="site-main" role="main">
                    <div id="prompt-search-results"></div>

                </main>
                <!-- #main -->
            </div>
            <!-- #primary -->

            <div id="secondary" class="column half">
                <div id="sidebar-1" class="widget-area" role="complementary">
                    <div id="selected-book-instance"></div>
                </div>
                <!-- .widget-area -->
            </div>
            <!-- #secondary -->
        </div>
        <!-- #content -->
    </div>
    <!-- .container -->
</div>
    <!-- #page -->

    <script>
        <%
            out.println("const username = '" + controller.getUsername(request) + "'");
            out.println("const isLibrarian = " + controller.hasRole(request, "librarian"));
        %>
    </script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/pages/JS/index.js"></script>
</body>
</html>