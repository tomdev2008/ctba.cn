var shortPass 	= 'Too short'
var longPass 	= 'Too long'
var badPass 	= 'Weak'
var goodPass 	= 'Strong'
var strongPass 	= 'Good'
function passwordStrength(password)
{
    score = 0 
    if (password.length < 6 ) { return '<span id="gesamt" class="pw_strength_bar" style="color:#cc2a50;background:none">'+shortPass+'</span>' }
	if (password.length > 79 ) { return '<span id="gesamt" class="pw_strength_bar">'+longPass+'</span>' }
    score += password.length * 4
    score += ( checkRepetition(1,password).length - password.length ) * 1
    score += ( checkRepetition(2,password).length - password.length ) * 1
    score += ( checkRepetition(3,password).length - password.length ) * 1
    score += ( checkRepetition(4,password).length - password.length ) * 1
    if (password.match(/(.*[0-9].*[0-9].*[0-9])/))  score += 5 
    if (password.match(/(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])/)) score += 5 
    if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))  score += 5 
    if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/))  score += 10 
    if (password.match(/([!,@,#,$,%,^,&,*,?,_,~])/) && password.match(/([0-9])/))  score += 10 
    if (password.match(/([!,@,#,$,%,^,&,*,?,_,~])/) && password.match(/([a-zA-Z])/))  score += 10 
    if (password.match(/^\w+$/) || password.match(/^\d+$/) )  score -= 10 
    if ( score < 0 )  score = 0 
    if ( score > 100 )  score = 100 
    if (score < 45 )  return '<span id="gesamt" class="pw_strength_bar" style="width: '+score+'%">'+badPass+'</span>'
    if (score < 80 )  return '<span id="gesamt" class="pw_strength_bar" style="width: '+score+'%">'+goodPass+'</span>'
    return '<span id="gesamt" class="pw_strength_bar" style="width:'+score+'%">'+strongPass+'</span>'
}

function checkRepetition(pLen,str) {
    res = ""
    for ( i=0; i<str.length ; i++ ) {
        repeated=true
        for (j=0;j < pLen && (j+i+pLen) < str.length;j++)
            repeated=repeated && (str.charAt(j+i)==str.charAt(j+i+pLen))
        if (j<pLen) repeated=false
        if (repeated) {
            i+=pLen-1
            repeated=false
        }
        else {
            res+=str.charAt(i)
        }
    }
    return res
}