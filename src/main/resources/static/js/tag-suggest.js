$(function(){
  const data = [
       'sample1',
       'sample2',
       'sample3',
       'sample4',
       'test',
   ];

   $('#suggest').autocomplete({
       source: data,
       autoFocus: true,
       delay: 100,
       minLength: 1
   });
});