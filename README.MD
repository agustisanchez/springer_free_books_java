# Java command-line application to download all Springer books released for free during the 2020 COVID-19 quarantine

Please, go to https://github.com/alexgand/springer_free_books for the original Python script by alexgand.

## Requirements

You need Java 8 or later.

## Executable

The executable file is `springer.free.books-1.0.0.jar` and can be downloaded from [here](http://agustisanchez.net/public/springer.free.books-1.0.0.zip) (unzip the jar file).

### Build

Alternatively, you can download this repository and build the executable with `mvn package`.
You'll find the executable file in the `target` directory.

## Examples

Here are some examples for downloading the books:

Download all the books in the Chemistry and Computer Science categories (PDF format) and place them in the local directory "books".

```
java -jar springer.free.books-1.0.0.jar -c chemistry "computer science" -o books
```

Download all the books in the Chemistry and Computer Science categories, that are in ePUB format, and place them in the local directory "books".

```
java -jar springer.free.books-1.0.0.jar -c chemistry "computer science" -f epub -o books
```

Download all the books in the Chemistry and Computer Science categories, that are in either PDF or ePUB format, that are written in English and place them in the local directory "books".

```
java -jar springer.free.books-1.0.0.jar -c chemistry "computer science" -f pdf epub -l en -o books
```


### Source:
* https://group.springernature.com/gp/group/media/press-releases/freely-accessible-textbook-initiative-for-educators-and-students/17858180?utm_medium=social&utm_content=organic&utm_source=facebook&utm_campaign=SpringerNature_&sf232256230=1
* https://www.springernature.com/gp/librarians/news-events/all-news-articles/industry-news-initiatives/free-access-to-textbooks-for-institutions-affected-by-coronaviru/17855960
* https://resource-cms.springernature.com/springer-cms/rest/v1/content/17858272/data/v4

Thanks Springer!

