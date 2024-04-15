CREATE TABLE "book" (
"id" BIGSERIAL,
"author" TEXT,
"book_name" TEXT,
"year_of_publishing" INTEGER,
"cost" DECIMAL,

CONSTRAINT "book_pk" PRIMARY KEY ("id")
);